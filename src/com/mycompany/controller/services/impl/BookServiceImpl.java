package com.mycompany.controller.services.impl;

import com.mycompany.controller.services.BookService;
import com.mycompany.model.bean.Book;
import com.mycompany.model.dao.BookDao;
import com.mycompany.model.dao.impl.BookDaoImpl;
import com.mycompany.model.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookServiceImpl implements BookService{
    BookDao bookDao = new BookDaoImpl();
    Connection conn;

    {
        try {
            conn = JdbcUtils.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> findByTitle(String title) {
        return bookDao.selectByTitle(conn,title);
    }

    @Override
    public List<Book> selectAllBooks() {
        try {

            List<Book> books = bookDao.selectAll(conn);
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
