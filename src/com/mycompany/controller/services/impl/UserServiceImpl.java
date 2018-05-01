package com.mycompany.controller.services.impl;

import com.mycompany.controller.services.AbstractUserService;
import com.mycompany.model.bean.AbstractUser;
import com.mycompany.model.bean.Book;
import com.mycompany.model.bean.User;
import com.mycompany.model.dao.UserDao;
import com.mycompany.model.dao.impl.UserDaoImpl;
import com.mycompany.model.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements AbstractUserService<User> {
    private Connection conn = JdbcUtils.getConnection();
    private UserDao userDao = new UserDaoImpl(); // 用户数据访问对象

    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    // 实例化UserServiceImpl的时候获取一个数据库连接对象
    public UserServiceImpl() throws SQLException{
        this.conn = JdbcUtils.getConnection();
    }

    @Override
    public User register(User user) throws SQLException {
        return null;
    }

    @Override
    public User login(User user) throws SQLException {
        User u = userDao.selectByAccount(conn,user.getAccount());
        return u;
    }

    @Override
    public User rename(User user) throws SQLException {
        return null;
    }

    @Override
    public User changePwd(User user) throws SQLException {
        return null;
    }

    @Override
    public List<Book> showAllBooks() throws SQLException {
        return null;
    }


    /**
     * 登录：至少需要三个参数: 账号,密码,账户类型
     * */

}
