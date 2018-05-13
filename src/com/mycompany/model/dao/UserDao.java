package com.mycompany.model.dao;

import com.mycompany.model.bean.Card;
import com.mycompany.model.bean.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDao extends BaseDao<User>{
    /**
     * 通过账号来查找一个用户，账号是唯一的
     * @return 返回查找到的User对象
     * */
    User insert(Connection conn, User user) throws SQLException;
    /**
     * 通过账号来查找用户
     * @param account 用户账号
     * @param conn 数据库连接对象
     * @return 用找到的用户的数据创建一个用户对象并返回这个对象
     * */
    User selectByAccount(Connection conn, String account) throws SQLException;
    /**
     * 通过账号和密码来查找一个用户，可以用来验证账号密码
     * @return 返回查找到的User对象
     * */
    User selectByAccountAndPassword(Connection conn, String account, String password) throws SQLException;
    /**
     * 通过名字选择用户
     * @param conn 数据库连接
     * @param name 用户名
     * */
    List<User> selectByName(Connection conn, String name) throws SQLException;
    /**
     * 用户修改名字
     * @param conn 数据库连接
     * @param u 要修改用户名的用户对象
     * @param name 新的密码
     * @return 返回修改好的用户对象
     * */
    User updateName(Connection conn, User u, String name) throws SQLException;
    /**
     * 用户修改密码
     * @param conn 数据库连接
     * @param u 要修改用户名的用户对象
     * @param password 新的密码
     * @return 返回修改好的用户对象
     * */
    User updatePassword(Connection conn, User u, String password) throws SQLException;
    /**
     * 更新用户的借书卡
     * 注意：新用户申请一张借书卡时必须先在借书卡表中创建借书卡
     * */
    User updateCard(Connection conn, User u,Card card) throws SQLException;
    User findUserByCardId(Connection conn, Integer card_id) throws SQLException;
}
