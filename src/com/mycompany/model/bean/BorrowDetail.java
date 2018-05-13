package com.mycompany.model.bean;

public class BorrowDetail {
    private Integer book_id;
    private String title;
    private String author;
    private Double price;
    private String borrow_time;
    private String return_time;

    public BorrowDetail() {
    }

    public BorrowDetail(Integer book_id, String title, String author, Double price, String borrow_time, String return_time) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.borrow_time = borrow_time;
        this.return_time = return_time;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BorrowDetail{");
        sb.append("book_id=").append(book_id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", price=").append(price);
        sb.append(", borrow_time='").append(borrow_time).append('\'');
        sb.append(", return_time='").append(return_time).append('\'');
        sb.append('}');
        return sb.toString();
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

    public String getBorrow_time() {
        return borrow_time;
    }

    public void setBorrow_time(String borrow_time) {
        this.borrow_time = borrow_time;
    }

    public String getReturn_time() {
        return return_time;
    }

    public void setReturn_time(String return_time) {
        this.return_time = return_time;
    }
}
