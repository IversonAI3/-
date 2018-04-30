package com.mycompany.model.jdbc;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 这是一个JDBC工具类
 * 使用c3p0来获得一个ComboPooledDataSource资源池
 * 可以从资源池中获得数据源DataSource，也可以直接获得一个数据库连接对象Connection
 * */
public class JdbcUtils {

    private static ComboPooledDataSource pool = new ComboPooledDataSource();

    /**
     * 缺少一个关闭数据库连接，释放资源的方法。
     * 只能依靠c3p0自动关闭和释放。
     * */

    public static Connection getConnection() throws SQLException {
        return pool.getConnection();
    }
}
