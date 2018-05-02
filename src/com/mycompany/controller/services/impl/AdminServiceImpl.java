package com.mycompany.controller.services.impl;

import com.mycompany.controller.services.AbstractUserService;
import com.mycompany.model.bean.AbstractUser;
import com.mycompany.model.bean.Admin;
import com.mycompany.model.bean.Book;
import com.mycompany.model.bean.User;
import com.mycompany.model.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminServiceImpl implements AbstractUserService<Admin>{
    private static Connection conn;

    static {
        try {
            conn = JdbcUtils.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Admin register(Admin admin) throws SQLException {
        return null;
    }

    @Override
    public Admin findByAccount(Admin admin) {
        return null;
    }

    @Override
    public Admin login(Admin admin) throws SQLException {
        return null;
    }

    @Override
    public Admin rename(Admin admin) throws SQLException {
        return null;
    }

    @Override
    public Admin changePwd(Admin admin) throws SQLException {
        return null;
    }

    @Override
    public List<Book> showAllBooks() throws SQLException {
        return null;
    }

    @Override
    public Admin findByAccountAndPassword(AbstractUser user) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String account = user.getAccount();
        String password = user.getPassword();
        sb.append("SELECT * FROM `admin` WHERE `account` = '")
                .append(account)
                .append("' AND `password` = '").append(password).append("';");
        System.out.println(sb.toString());
        try {
            ResultSet resultSet = conn.createStatement().executeQuery(sb.toString());
            if(!resultSet.next()){
                user = null;
            }else{
                user = new Admin();
                user.setAccount(resultSet.getString("account"));
                user.setPassword(resultSet.getString("password"));
                System.out.println(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return (Admin) user;
    }
}
