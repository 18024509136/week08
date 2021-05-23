package com.geek.shardingtest;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.*;

/**
 * 实现等值查询或范围查询的数据库寻址
 *
 * @author huangxiaodi
 * @since 2021-05-19 14:28
 */
public class DatabaseSharding implements PreciseShardingAlgorithm<String>, RangeShardingAlgorithm<String> {

    /**
     * 数据库后缀表征的下限
     */
    private static final DateTime LOWER_BOUND = DateUtil.parse("2021-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss");

    /**
     * 数据库后缀表征的上限
     */
    private static final DateTime UPPER_BOUND = DateUtil.parse("2021-04-30 00:00:00", "yyyy-MM-dd HH:mm:ss");

    /**
     * 等值查询寻址逻辑，如2021-01-01 09:00:00则寻址到逻辑数据库ds202101
     *
     * @param collection           所有的数据库名称集合
     * @param preciseShardingValue 封装了sql中的等值比较值
     * @return
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        String suffix = buildSuffixByDateTimeStr(preciseShardingValue.getValue());

        for (String database : collection) {
            if (database.endsWith(suffix)) {
                return database;
            }
        }

        return null;
    }

    /**
     * 范围查询寻址逻辑，如下限值2021-01-01 09:00:00和上限值2021-03-01 09:00:00，则寻址到逻辑数据库ds202101、ds202102、ds202103
     *
     * @param collection         所有的数据库名称集合
     * @param rangeShardingValue 封装了sql中上限值和下限值的对象
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<String> rangeShardingValue) {
        List<String> targetDatabases = new ArrayList<>(2);

        DateTime lower;
        DateTime upper;

        String upperString = rangeShardingValue.getValueRange().hasUpperBound() ? rangeShardingValue.getValueRange().upperEndpoint() : "";
        String lowerString = rangeShardingValue.getValueRange().hasLowerBound() ? rangeShardingValue.getValueRange().lowerEndpoint() : "";

        if (!upperString.isEmpty() && !lowerString.isEmpty()) {
            lower = DateUtil.parse(lowerString, "yyyy-MM-dd HH:mm:ss");
            upper = DateUtil.parse(upperString, "yyyy-MM-dd HH:mm:ss");
        } else if (upperString.isEmpty() && !lowerString.isEmpty()) {
            lower = DateUtil.parse(lowerString, "yyyy-MM-dd HH:mm:ss");
            upper = UPPER_BOUND;
        } else {
            lower = LOWER_BOUND;
            upper = DateUtil.parse(upperString, "yyyy-MM-dd HH:mm:ss");
        }

        DateTime currentDate = lower;
        // 从lower所在的月份一直增加到upper所在月份
        while (currentDate.compareTo(upper) <= 0) {
            for (String database : collection) {
                String suffix = buildSuffixByDateTime(currentDate);
                String databaseSuffix = database.substring(2);
                if (databaseSuffix.equals(suffix) && !targetDatabases.contains(database)) {
                    targetDatabases.add(database);
                }

            }

            currentDate = DateUtil.offsetMonth(currentDate, 1);
        }

        return targetDatabases;
    }

    /**
     * 通过时间字符串获取分库后缀，如2021-01-01 09:30:00，则后缀为202101
     *
     * @param dateTimeString
     * @return
     */
    private String buildSuffixByDateTimeStr(String dateTimeString) {
        DateTime dateTime = DateUtil.parseDateTime(dateTimeString);
        return buildSuffixByDateTime(dateTime);
    }

    private String buildSuffixByDateTime(DateTime dateTime) {
        int year = dateTime.getField(DateField.YEAR);
        int month = dateTime.getField(DateField.MONTH) + 1;
        String monthStr;
        if (month >= 10) {
            monthStr = String.valueOf(month);
        } else {
            monthStr = "0" + month;
        }

        StringBuilder suffixBuilder = new StringBuilder()
                .append(year)
                .append(monthStr);

        return suffixBuilder.toString();
    }
}
