package com.mycompany.model.dao.impl;

import com.mycompany.model.bean.Book;
import com.mycompany.model.dao.BookDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BookDaoImpl extends BaseDaoImpl<Book> implements BookDao{
    @Override
    public List<Book> selectByTitle(Connection connection,String title) {
        StringBuilder sb = new StringBuilder();
        // SELECT * FROM Persons WHERE City LIKE '%lond%'
        sb.append("SELECT * FROM `book` WHERE `title` LIKE ")
                .append("'%").append(title).append("%';");
        System.out.println(sb);
        List<Book> bookList;
        try {
            Book book;
            ResultSet rs = connection.createStatement().executeQuery(sb.toString());
            bookList = new LinkedList<>();// ArrayList插入效率比较低
            while (rs.next()){
                book = new Book();
                book.setAuthor(rs.getString("author"));
                book.setBook_id(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setPrice(rs.getDouble("price"));
                book.setQuantity(rs.getInt("quantity"));
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        bookList.forEach(b-> System.out.println(b));
        return bookList;
    }
}
