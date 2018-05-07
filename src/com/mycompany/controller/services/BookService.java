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
    List<Book> findByTitle(String title) throws SQLException;
    List<Book> selectAllBooks() throws SQLException;
    /**
     * 查找某一个用户借的所有书
     * */
    List<Book> selectBorrowedBooksByUser(User u) throws SQLException;
    /**
     * 通过一个book_id来查询书的数量
     * @param book_id
     * @return 返回书的数量，如果book_id对应的书不存在，则返回null
     * */
    Integer getQuantityByBookId(Integer book_id) throws SQLException;
    /**
     * 添加一本新书
     * @param book
     * @return 添加成功返回Book对象，否则返回null
     * */
    Book addNewBook(Book book) throws SQLException;
    /**
     * 更新一本书
     * @param book
     * @return 更新成功返回book对象，否则返回null
     * */
    Book updateBook(Book book) throws SQLException;
}
