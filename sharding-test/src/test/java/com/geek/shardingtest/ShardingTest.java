package com.geek.shardingtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 总体需求：
 * 根据订单的创建时间所在的年份月份来分库，如2021-01-01 09:00:00则路由到ds_202101数据库
 * 在根据订单的用户ID的哈希取余来确定分表，如分表数为16个，那么用户ID为1的数据则路由到order_info_1表
 */
public class ShardingTest extends ShardingTestApplicationTests {

    @Autowired
    private DataSource dataSource;

    /**
     * 插入数据
     *
     * @throws Exception
     */
    @Test
    public void insertTest() throws Exception {
        // 为了测试范围查询，定义订单几个固定的创建时间
        String[] createTimeArr = {
                "2021-01-02 09:00:00",
                "2021-01-02 10:00:00",
                "2021-02-02 09:00:00",
                "2021-02-02 10:00:00",
                "2021-03-02 09:00:00",
                "2021-03-02 09:30:00",
                "2021-03-02 10:00:00",
                "2021-03-02 10:30:00",
                "2021-04-02 09:00:00",
                "2021-04-02 09:00:00"
        };

        Connection connection = dataSource.getConnection();
        String sql = "INSERT INTO order_info (user_id, user_addr_id, pay_channel, total_num, total_amount, express_price, status,create_time) values (?,?,?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);

        for (int i = 0; i < 10; i++) {
            ps.setLong(1, 1L);
            ps.setString(2, "1");
            ps.setInt(3, 1);
            ps.setInt(4, 1);
            ps.setInt(5, 100000);
            ps.setInt(6, 12000);
            ps.setInt(7, 1);
            ps.setString(8, createTimeArr[i]);

            ps.executeUpdate();
            ps.clearParameters();
        }

        closeResource(ps, null, connection);
    }

    /**
     * 根据订单号更新订单信息
     *
     * @throws Exception
     */
    @Test
    public void updateTest() throws Exception {
        Connection connection = dataSource.getConnection();
        String sql = "update order_info set pay_channel = ? where order_no = ? and create_time = ? and user_id = ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, 3);
        ps.setLong(2, 602092352177180672L);
        ps.setString(3, "2021-02-02 09:00:00");
        ps.setLong(4, 1L);

        ps.executeUpdate();

        closeResource(ps, null, connection);
    }

    /**
     * 根据订单号查询订单信息
     *
     * @throws Exception
     */
    @Test
    public void queryTest() throws Exception {
        Connection connection = dataSource.getConnection();
        String sql = "select order_no, user_id, create_time from order_info where order_no = ? and create_time = ? and user_id = ? ";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, 602092352177180672L);
        ps.setString(2, "2021-02-02 09:00:00");
        ps.setLong(3, 1L);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getLong(1));
            System.out.println(rs.getLong(2));
            System.out.println(rs.getTimestamp(3));
        }

        closeResource(ps, rs, connection);
    }

    /**
     * 根据用户ID和订单创建时间范围查询
     *
     * @throws Exception
     */
    @Test
    public void rangeQueryTest() throws Exception {
        Connection connection = dataSource.getConnection();
        String sql = "select order_no, user_id, create_time from order_info where user_id = ? and create_time >= ? and create_time < ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, 1);
        ps.setString(2, "2021-02-02 09:00:00");
        ps.setString(3, "2021-03-02 10:30:00");

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getLong(1));
            System.out.println(rs.getLong(2));
            System.out.println(rs.getTimestamp(3));
            System.out.println("================");
        }

        closeResource(ps, rs, connection);
    }

    /**
     * 根据订单号删除订单
     *
     * @throws Exception
     */
    @Test
    public void deleteTest() throws Exception {
        Connection connection = dataSource.getConnection();
        String sql = "delete from order_info where order_no = ? and create_time = ? and user_id = ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, 602086055713181696L);
        ps.setString(2, "2021-02-02 09:00:00");
        ps.setLong(3, 1L);

        ps.executeUpdate();

        closeResource(ps, null, connection);
    }

    private static void closeResource(PreparedStatement ps, ResultSet rs, Connection connection) {

        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
