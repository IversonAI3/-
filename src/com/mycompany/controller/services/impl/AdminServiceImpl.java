package com.mycompany.controller.services.impl;

import com.mycompany.controller.services.AbstractUserService;
import com.mycompany.model.bean.AbstractUser;
import com.mycompany.model.bean.Admin;
import com.mycompany.model.bean.Book;
import com.mycompany.model.bean.User;
import com.mycompany.model.dao.AdminDao;
import com.mycompany.model.dao.impl.AdminDaoImpl;
import com.mycompany.model.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminServiceImpl implements AbstractUserService<Admin>{
    private Connection conn;
    private AdminDao adminDao = new AdminDaoImpl();

    public AdminServiceImpl(){
        try {
            this.conn = JdbcUtils.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Admin register(Admin admin) throws SQLException {
        return null;
    }

    @Override
    public Admin findByAccount(String account) {
        return null;
    }

    @Override
    public Admin login(Admin admin) throws SQLException {
        return null;
    }

    @Override
    public Admin rename(Admin admin, String name) throws SQLException {
        return null;
    }

    @Override
    public Admin changePwd(Admin admin, String pwd) throws SQLException {
        return null;
    }


    @Override
    public List<Book> showAllBooks() throws SQLException {
        return null;
    }

    @Override
    public Admin findByAccountAndPassword(String account, String pwd) throws SQLException {
        Admin admin = adminDao.selectByAccountAndPassword(conn,account,pwd);
        System.out.println(admin);
        return admin;
    }
}
