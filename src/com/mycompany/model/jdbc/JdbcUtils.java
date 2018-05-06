package com.mycompany.model.jdbc;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * JdbcUtils是一个JDBC静态工具类
 * 使用c3p0来获得一个ComboPooledDataSource资源池
 * 可以从资源池中获得数据源DataSource，也可以直接获得一个数据库连接对象Connection
 * 注意: 缺少一个关闭数据库连接释放资源的方法。只能依靠c3p0自动关闭和释放。
 * */
public class JdbcUtils {

    private static ComboPooledDataSource pool = new ComboPooledDataSource();
    private static Connection connection;

    static {
        try{
            connection = pool.getConnection();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 禁止实例化一个工具类
     * */
    private JdbcUtils(){}
    /**
     * 通过c3p0直接获得一个Connection对象
     * @return Connection对象
     * */
    public static Connection getConnection(){
        System.out.println("获得连接Connection: "+connection.toString());
        return connection;
    }
}
