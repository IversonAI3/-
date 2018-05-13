package com.mycompany.controller.services.impl;

import com.mycompany.controller.services.BookService;
import com.mycompany.model.bean.Book;
import com.mycompany.model.bean.BorrowDetail;
import com.mycompany.model.bean.User;
import com.mycompany.model.dao.BookDao;
import com.mycompany.model.dao.impl.BookDaoImpl;
import com.mycompany.model.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookServiceImpl implements BookService{
    BookDao bookDao = new BookDaoImpl();
    Connection conn = JdbcUtils.getConnection();

    @Override
    public List<Book> findByTitle(String title) throws SQLException {
        return bookDao.selectByTitle(conn,title);
    }

    @Override
    public List<Book> selectAllBooks() throws SQLException{
        List<Book> books = bookDao.selectAll(conn);
        return books;
    }

    @Override
    public List<BorrowDetail> selectBorrowedBooksByUser(User u) throws SQLException {
        List<BorrowDetail> books = bookDao.selectByUserId(conn,u.getUser_id());
        return books;
    }

    @Override
    public Integer getQuantityByBookId(Integer book_id) throws SQLException{
        Integer quanaity = bookDao.selectQuantityById(conn, book_id);
        return quanaity;
    }

    @Override
    public Book addNewBook(Book book) throws SQLException {
        return bookDao.insert(conn,book);
    }

    @Override
    public Book updateBook(Book book) throws SQLException {
        return bookDao.update(conn,book);
    }

    @Override
    public Book selectByBookId(Integer id) throws SQLException {
        Book book = bookDao.findByBookId(conn,id);
        return book;
    }
}
