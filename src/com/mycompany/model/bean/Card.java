package com.mycompany.model.bean;

public class Card {
    private int card_id;
    private Double balance;
    private Short amount;
    private Short quota;

    public Card() {
    }

    public Card(int card_id, Double balance, Short amount, Short quota) {
        this.card_id = card_id;
        this.balance = balance;
        this.amount = amount;
        this.quota = quota;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Short getAmount() {
        return amount;
    }

    public void setAmount(Short amount) {
        this.amount = amount;
    }

    public Short getQuota() {
        return quota;
    }

    public void setQuota(Short quota) {
        this.quota = quota;
    }

    @Override
    public String toString() {
        return "Card{" +
                "card_id=" + card_id +
                ", balance=" + balance +
                ", amount=" + amount +
                ", quota=" + quota +
                '}';
    }
}
