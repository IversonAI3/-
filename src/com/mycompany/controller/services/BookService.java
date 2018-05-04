package com.mycompany.controller.services;

import com.mycompany.model.bean.Book;
import com.mycompany.model.bean.User;

import java.sql.SQLException;
import java.util.List;

public interface BookService {
    /**
     * 通过用户提供的书名返回查询到的书，可能存在很多本同名的书
     * @param title 书名
     * @return 查询到的书的集合
     * */
    List<Book> findByTitle(String title);
    List<Book> selectAllBooks();
    /**
     * 查找某一个用户借的所有书
     * */
    List<Book> selectBorrowedBooksByUser(User u) throws SQLException;
}
