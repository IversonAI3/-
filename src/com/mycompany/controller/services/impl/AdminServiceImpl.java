package com.mycompany.controller.services.impl;

import com.mycompany.controller.services.AbstractUserService;
import com.mycompany.model.bean.*;
import com.mycompany.model.dao.*;
import com.mycompany.model.dao.impl.*;
import com.mycompany.model.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AdminServiceImpl implements AbstractUserService<Admin>{
    private Connection conn;
    private AdminDao adminDao = new AdminDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private BookDao bookDao = new BookDaoImpl();
    private BorrowRecordDao borrowRecordDao = new BorrowRecordDaoImpl();
    private ReturnRecordDao returnRecordDao = new ReturnRecordDaoImpl();

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
        return findByAccountAndPassword(admin.getAccount(),admin.getPassword());
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
    public Admin findByAccountAndPassword(String account, String pwd) throws SQLException {
        return adminDao.selectByAccountAndPassword(conn,account,pwd);
    }

    public List<User> showAllUsers() throws SQLException {
        return userDao.selectAll(conn);
    }

    public List<BorrowRecord> showAllBorrowRecords() throws SQLException{
        return borrowRecordDao.selectAll(conn);
    }

    public List<ReturnRecord> showAllReturnRecords() throws SQLException{
        return returnRecordDao.selectAll(conn);
    }

    public boolean deleteBookById(Book book) throws SQLException {
        Book b = bookDao.delete(conn,book);
        return b==null?false:true;
    }

    public List<BorrowRecord> showAllRecordsByBook(Book book) throws SQLException {
        List<BorrowRecord> borrowRecords = borrowRecordDao.selectByBookId(conn,book.getBook_id());
        return borrowRecords;
    }

}
