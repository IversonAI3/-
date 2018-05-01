package com.mycompany.model.bean;

public class Book {
    private Integer book_id;
    private String title;
    private String author;
    private Double price;
    private Integer quantity;

    public Book(Integer book_id, String title, String author, Double price, Integer quantity) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
    }

    public Book() {}

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
}
