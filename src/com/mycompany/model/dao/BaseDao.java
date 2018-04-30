package com.mycompany.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/*
* Base Data Access Object是一个父类接口，规定了所有的Service都必须要使用的基本操作
* */
public interface BaseDao<T> {

    /**
     * 在数据库中插入一行数据
     * @param conn 连接对象， t 要插入的对象
     * @return 返回一个int表示受影响的行数
     * */
    int insert(Connection conn, T t) throws SQLException;

    /**
     * 在数据库中更新一行数据
     * @param conn 连接对象， t 一个新对象，代表要插入的数据
     * @return 返回一个int表示受影响的行数
     * */
    int update(Connection conn, T t) throws SQLException;

    /**
     * 在数据库中删除一行数据
     * @param conn 连接对象， id 要删除的数据的主键
     * @return 返回一个int表示受影响的行数
     * */
    int deleteById(Connection conn, int id) throws SQLException;

    /**
     * 在数据库中根据主键查询一行数据
     * @param conn 连接对象， id 主键
     * @return 返回查询到的数据
     * */
    T findById(Connection conn, int id) throws SQLException;

    /**
     * 在数据库中查询某个表中所有的数据
     * @param conn 连接对象
     * @return 返回一个List包含所有的数据对象
     * */
    List<T> selectAll(Connection conn) throws SQLException;

    /**
     * 查询数据库中的某个表共有多少行数据
     * @param conn 连接对象
     * @return 返回一个int表示总共有多少行
     * */
    int selectCount(Connection conn) throws SQLException;

    void test(Connection conn) throws SQLException;
}
