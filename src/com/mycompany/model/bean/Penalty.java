package com.mycompany.model.bean;

public class Penalty {
    private int penalty_id;
    private int card_id;
    private int record_id;
    private int admin_id;
    private Double amound;

    public Penalty() {}

    public Penalty(int penalty_id, int card_id, int record_id, int admin_id, Double amound) {
        this.penalty_id = penalty_id;
        this.card_id = card_id;
        this.record_id = record_id;
        this.admin_id = admin_id;
        this.amound = amound;
    }

    public int getPenalty_id() {
        return penalty_id;
    }

    public void setPenalty_id(int penalty_id) {
        this.penalty_id = penalty_id;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public Double getAmound() {
        return amound;
    }

    public void setAmound(Double amound) {
        this.amound = amound;
    }

    @Override
    public String toString() {
        return "Penalty{" +
                "penalty_id=" + penalty_id +
                ", card_id=" + card_id +
                ", record_id=" + record_id +
                ", admin_id=" + admin_id +
                ", amound=" + amound +
                '}';
    }
}
