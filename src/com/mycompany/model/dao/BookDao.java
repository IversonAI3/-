package com.mycompany.model.dao;

import com.mycompany.model.bean.Book;
import com.mycompany.model.bean.BorrowDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BookDao extends BaseDao<Book>{
    /**
     * 通过书名来查找书，可能有多本同名的书
     * @param connection
     * @param title 书名
     * @return 所有该书的集合
     * */
    List<Book> selectByTitle(Connection connection, String title) throws SQLException;
    /**
     * 通过用户ID来查找这个用户借的所有书
     * @param connection 数据库连接对象
     * @param user_id 用户id
     * @return 该用户借的所有书的集合
     */
    List<BorrowDetail> selectByUserId(Connection connection, Integer user_id) throws SQLException;
    /**
     * 通过书的ID查询它数量
     * @param book_id
     * @return 返回书的数量，如果book_id对应的书不存在，则返回null
     */
    Integer selectQuantityById(Connection connection, Integer book_id) throws SQLException;
    /**
     * 返回最新添加的一本书
     * */
    Book selectAddedBook(Connection connection) throws SQLException;
    Book findByBookId(Connection conn, Integer id) throws SQLException;
}
