package com.mycompany.model.dao;

import com.mycompany.model.bean.Book;

import java.sql.Connection;
import java.util.List;

public interface BookDao extends BaseDao<Book>{
    List<Book> selectByTitle(Connection connection, String title);
}
