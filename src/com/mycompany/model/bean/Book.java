package com.mycompany.model.bean;

import java.util.Objects;

public class Book {
    private Integer book_id;
    private String title;
    private String author;
    private Double price;
    private String create_time;
    private String modify_time;
    private Integer quantity;

    public Book(Integer book_id, String title, String author, Double price, Integer quantity) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
    }

    public Book() {}

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getModify_time() {
        return modify_time;
    }

    public void setModify_time(String modify_time) {
        this.modify_time = modify_time;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + book_id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        // 只有当title和author都完全相同时，两本书才是完全相同的
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title) &&
                Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        // 用title和author来得到hash code，意味着title和author的组合必须是unique唯一的
        return Objects.hash(title, author);
    }
}
