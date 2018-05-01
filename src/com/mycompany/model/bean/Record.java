package com.mycompany.model.bean;

public class Record {
    private Integer record_id;
    private Integer card_id;
    private Integer book_id;
    private String borrow_time;
    private String return_time;

    public Record() {
    }

    public int getRecord_id() {
        return record_id;
    }

    public Record(Integer record_id, Integer card_id, Integer book_id, String borrow_time, String return_time) {
        this.record_id = record_id;
        this.card_id = card_id;
        this.book_id = book_id;
        this.borrow_time = borrow_time;
        this.return_time = return_time;
    }

    public void setRecord_id(Integer record_id) {
        this.record_id = record_id;
    }

    public Integer getCard_id() {
        return card_id;
    }

    public void setCard_id(Integer card_id) {
        this.card_id = card_id;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
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

    @Override
    public String toString() {
        return "Record{" +
                "record_id=" + record_id +
                ", card_id=" + card_id +
                ", book_id=" + book_id +
                ", borrow_time='" + borrow_time + '\'' +
                ", return_time='" + return_time + '\'' +
                '}';
    }
}
