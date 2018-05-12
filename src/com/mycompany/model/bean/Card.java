package com.mycompany.model.bean;

public class Card {
    private Integer card_id;
    private Double balance;
    private Short quota;

    public Card() {
    }

    public Card(Integer card_id, Double balance, Short quota) {
        this.card_id = card_id;
        this.balance = balance;
        this.quota = quota;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Card{");
        sb.append("card_id=").append(card_id);
        sb.append(", balance=").append(balance);
        sb.append(", quota=").append(quota);
        sb.append('}');
        return sb.toString();
    }

    public Integer getCard_id() {
        return card_id;
    }

    public void setCard_id(Integer card_id) {
        this.card_id = card_id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Short getQuota() {
        return quota;
    }

    public void setQuota(Short quota) {
        this.quota = quota;
    }
}
