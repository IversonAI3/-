package com.mycompany.model.bean;

public class Record {
    private int record_id;
    private int card_id;
    private int book_id;
    private String borrow_time;
    private String return_time;

    public Record(int record_id, int card_id, int book_id, String borrow_time, String return_time) {
        this.record_id = record_id;
        this.card_id = card_id;
        this.book_id = book_id;
        this.borrow_time = borrow_time;
        this.return_time = return_time;
    }

    public Record() {
    }

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
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
