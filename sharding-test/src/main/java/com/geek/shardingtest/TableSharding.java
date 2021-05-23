package com.geek.shardingtest;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 实现表寻址逻辑
 *
 * @author huangxiaodi
 * @since 2021-05-19 14:17
 */
public class TableSharding implements PreciseShardingAlgorithm<Long> {

    /**
     * 根据用户ID的hashcode%16取余
     *
     * @param collection
     * @param preciseShardingValue
     * @return
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        if (preciseShardingValue.getValue() != null) {
            int res = (int) (preciseShardingValue.getValue().longValue() % 16);
            String resStr = String.valueOf(res);

            for (String table : collection) {
                if (table.endsWith("_" + resStr)) {
                    return table;
                }
            }
        }
        return null;
    }
}
