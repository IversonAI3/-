package com.mycompany.controller.services.impl;

import com.mycompany.controller.services.UserService;
import com.mycompany.model.bean.Book;
import com.mycompany.model.bean.User;
import com.mycompany.model.dao.UserDao;
import com.mycompany.model.dao.impl.UserDaoImpl;
import com.mycompany.model.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService{
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
    public boolean register() throws SQLException {
        return false;
    }

    @Override
    public boolean login(User u) throws SQLException {
        User user = userDao.selectByAccount(conn,u.getAccount());
        if(user!=null){
            return true;
        }
        return false;
    }

    @Override
    public boolean rename(User u) throws SQLException {
        return false;
    }

    @Override
    public boolean changePwd(User u) throws SQLException {
        return false;
    }

    @Override
    public List<Book> showAllBooks() throws SQLException {
        return null;
    }


    /**
     * 登录：至少需要三个参数: 账号,密码,账户类型
     * */

}
