package com.mycompany.controller.services;


import com.mycompany.model.bean.AbstractUser;
import com.mycompany.model.bean.Book;
import com.mycompany.model.bean.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AbstractUserService<T extends AbstractUser> {
    /**
     * 用户的注册行为
     * @param t 接受一个用户对象作为参数
     * @return 如果注册成功返回这个对象，否则返回null
     * */
    T register(T t) throws SQLException;
    /**
     * 通过账号查询用户
     * @param account 要查询的用户的账号
     * @return 返回查询到的用户对象，如果没有查到返回null
     * */
    T findByAccount(String account) throws SQLException;
    /**
     * 用户的登录行为
     * @param t 接受一个用户对象作为参数
     * @return 如果登录成功：返回登录的那个User对象，如果失败返回null
     * */
    T login(T t) throws SQLException;
    /**
     * 用户修改姓名
     * @param t 传入一个用户对象
     * @param name 传入一个新的姓名
     * @return 修改好姓名的用户对象
     * */
    T rename(T t, String name) throws SQLException;
    /**
     * 用户的修改密码
     * @param t 要修改的用户对象
     * @param pwd 新的密码
     * @return 修改好密码的用户对象
     * */
    T changePwd(T t, String pwd) throws SQLException;
    /**
     * 查询所有的书
     * @return 包含所有书的list
     * */
    List<Book> showAllBooks() throws SQLException;
    /**
     * 通过用户账号和密码进行查询
     * @return 如果查到返回这个用户对象，否则返回null
     * */
    T findByAccountAndPassword(String account, String pwd) throws SQLException;
}
