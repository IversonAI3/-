package com.mycompany.controller.services;

import com.mycompany.model.bean.Book;

import java.util.List;

public interface BookService {
    Book findByTitle();
    List<Book> selectAllBooks();
}
