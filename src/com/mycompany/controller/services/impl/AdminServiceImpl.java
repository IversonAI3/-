package com.mycompany.controller.services.impl;

import com.mycompany.controller.services.AbstractUserService;
import com.mycompany.model.bean.AbstractUser;
import com.mycompany.model.bean.Admin;
import com.mycompany.model.bean.Book;
import com.mycompany.model.bean.User;

import java.sql.SQLException;
import java.util.List;

public class AdminServiceImpl implements AbstractUserService<Admin>{

    @Override
    public Admin register(Admin admin) throws SQLException {
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
}
