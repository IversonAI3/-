package com.mycompany.model.dao;

import com.mycompany.model.bean.Book;

import java.sql.Connection;
import java.util.List;

public interface BookDao extends BaseDao<Book>{
    List<Book> selectByTitle(Connection connection, String title);
    /**
     * 通过用户ID来查找这个用户借的所有书
     */
    List<Book> selectByUserId(Connection connection, Integer user_id);
}
