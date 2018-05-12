package com.mycompany.model.dao.impl;

import com.mycompany.model.bean.Book;
import com.mycompany.model.dao.BookDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BookDaoImpl extends BaseDaoImpl<Book> implements BookDao{
    @Override
    public List<Book> selectByTitle(Connection connection,String title) throws SQLException{
        StringBuilder sb = new StringBuilder();
        // WHERE City LIKE '%lond%'
        sb.append("SELECT * FROM `book` WHERE `title` LIKE ")
                .append("'%").append(title).append("%';");
        List<Book> bookList;
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
        bookList.forEach(b-> System.out.println(b));
        return bookList;
    }

    @Override
    public List<Book> selectByUserId(Connection connection, Integer user_id) throws SQLException{
        // SELECT b.*
        //            FROM `book` b
        //            INNER JOIN `record` r ON b.book_id = r.book_id
        //            INNER JOIN `user` u ON u.card_id = r.card_id
        StringBuilder sb = new StringBuilder();
        // WHERE City LIKE '%lond%'
        sb.append("SELECT b.* FROM `book` b ")
                .append("INNER JOIN `record` r ON b.book_id = r.book_id ")
                .append("INNER JOIN `user` u ON u.card_id = r.card_id ")
                .append("WHERE u.user_id=").append(user_id).append(";");
        System.out.println(sb);
        List<Book> bookList;
        Book book;
        ResultSet rs = connection.createStatement().executeQuery(sb.toString());
        bookList = new LinkedList<>();// ArrayList插入效率比较低
        while (rs.next()) {
            book = new Book();
            initializeBook(rs,book);
        }
        return bookList;
    }

    @Override
    public Integer selectQuantityById(Connection connection, Integer book_id) throws SQLException {
        // SELECT `quantity` FROM `book` WHERE `book_id` = 1;
        PreparedStatement ps = connection
                .prepareStatement("SELECT `quantity` FROM `book` WHERE `book_id`=?");
        ps.setInt(1, book_id.intValue());
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return rs.getInt(1);
        return null;
    }

    @Override
    public Book selectAddedBook(Connection connection) throws SQLException {
        // SELECT * FROM book b WHERE b.create_time = (SELECT MAX(create_time) FROM book);
        String str = "SELECT b.* FROM `book` b WHERE b.create_time = (SELECT MAX(create_time) FROM `book`);";
        ResultSet rs = connection.createStatement().executeQuery(str);
        if(rs.next()){
            Book book = new Book();
            initializeBook(rs,book);
            return book;
        }
        return null;
    }


    @Override
    public Book insert(Connection conn, Book book) throws SQLException {
        String title = book.getTitle();
        String author = book.getAuthor();
        Double price = book.getPrice();
        Integer quantity = book.getQuantity();
        String update = "INSERT INTO `book`(title, author, price, quantity) VALUES(?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(update);
        if(title==null||author==null||price==null||quantity==null)
            return null;
        ps.setString(1,title);
        ps.setString(2,author);
        ps.setDouble(3,price);
        ps.setInt(4,quantity);
        int row = ps.executeUpdate();
        return row==1 ? selectAddedBook(conn):null;
    }

    @Override
    public Book update(Connection conn, Book book) throws SQLException {
        String update = "UPDATE `book` SET title=?, author=?, price=?, quantity=? WHERE `book_id`=?;";
        PreparedStatement ps = conn.prepareStatement(update);
        ps.setString(1,book.getTitle());
        ps.setString(2,book.getAuthor());
        ps.setDouble(3,book.getPrice());
        ps.setInt(4,book.getQuantity());
        ps.setInt(5,book.getBook_id());
        int row = ps.executeUpdate();
        return row==1 ? book : null;
    }

    @Override
    public Book delete(Connection conn, Book book) throws SQLException {
        PreparedStatement ps =
                conn.prepareStatement("DELETE FROM `book` WHERE `book_id`=?;");
        ps.setInt(1,book.getBook_id());
        int i = ps.executeUpdate();
        return i==0?null:book;
    }

    @Override
    public Book find(Connection conn, Book book) throws SQLException {
        return null;
    }

    @Override
    public Book findByBookId(Connection conn, Integer id) throws SQLException {
        PreparedStatement ps
                = conn.prepareStatement("SELECT * FROM `book` WHERE `book_id`=?");
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            Book b = new Book();
            initializeBook(rs,b);
            return b;
        }
        return null;
    }

    /**
     * 用ResultSet结果集返回的一行结果来初始化Book对象
     * */
    private void initializeBook(ResultSet rs,Book book) throws SQLException {
        book.setAuthor(rs.getString("author"));
        book.setBook_id(rs.getInt("book_id"));
        book.setTitle(rs.getString("title"));
        book.setPrice(rs.getDouble("price"));
        book.setQuantity(rs.getInt("quantity"));
    }
}
