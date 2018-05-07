package com.mycompany.controller.services.impl;

import com.mycompany.controller.services.AbstractUserService;
import com.mycompany.model.bean.*;
import com.mycompany.model.dao.*;
import com.mycompany.model.dao.impl.AdminDaoImpl;
import com.mycompany.model.dao.impl.BookDaoImpl;
import com.mycompany.model.dao.impl.RecordDaoImpl;
import com.mycompany.model.dao.impl.UserDaoImpl;
import com.mycompany.model.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminServiceImpl implements AbstractUserService<Admin>{
    private Connection conn;
    private AdminDao adminDao = new AdminDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private BookDao bookDao = new BookDaoImpl();
    private RecordDao recordDao = new RecordDaoImpl();

    public AdminServiceImpl(){
        this.conn = JdbcUtils.getConnection();
    }

    @Override
    public Admin register(Admin admin)throws SQLException  {
        return null;
    }

    @Override
    public Admin findByAccount(String account) {
        return null;
    }

    @Override
    public Admin login(Admin admin)throws SQLException {
        return null;
    }

    @Override
    public Admin rename(Admin admin, String name) throws SQLException {
        return null;
    }

    @Override
    public Admin changePwd(Admin admin, String pwd)throws SQLException {
        return null;
    }


    @Override
    public List<Book> showAllBooks() throws SQLException {
        return bookDao.selectAll(conn);
    }

    @Override
    public Admin findByAccountAndPassword(String account, String pwd) {
        return adminDao.selectByAccountAndPassword(conn,account,pwd);
    }

    public List<User> showAllUsers() throws SQLException {
        return userDao.selectAll(conn);
    }

    public List<Record> showAllRecordsByBook(Book book) throws SQLException{
        return recordDao.selectByBookId(conn,book.getBook_id());
    }

    public boolean deleteBookById(Book book) throws SQLException {
        Book b = bookDao.delete(conn,book);
        return b==null?false:true;
    }
}
