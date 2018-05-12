package com.mycompany.model.bean;

public class ReturnRecord {
    private Integer return_id;
    private Integer book_id;
    private Integer card_id;
    private String borrow_time;
    private String return_time;

    public ReturnRecord() {
    }

    public ReturnRecord(Integer return_id, Integer book_id, Integer card_id, String borrow_time, String return_time) {
        this.return_id = return_id;
        this.book_id = book_id;
        this.card_id = card_id;
        this.borrow_time = borrow_time;
        this.return_time = return_time;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReturnRecord{");
        sb.append("return_id=").append(return_id);
        sb.append(", book_id=").append(book_id);
        sb.append(", card_id=").append(card_id);
        sb.append(", borrow_time='").append(borrow_time).append('\'');
        sb.append(", return_time='").append(return_time).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Integer getReturn_id() {
        return return_id;
    }

    public void setReturn_id(Integer return_id) {
        this.return_id = return_id;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public Integer getCard_id() {
        return card_id;
    }

    public void setCard_id(Integer card_id) {
        this.card_id = card_id;
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
