package com.mycompany.model.bean;

public class Penalty {
    private Integer penalty_id;
    private Integer card_id;
    private Integer record_id;
    private Integer admin_id;
    private Double amound;

    public Penalty() {}

    public Penalty(Integer penalty_id, Integer card_id, Integer record_id, Integer admin_id, Double amound) {
        this.penalty_id = penalty_id;
        this.card_id = card_id;
        this.record_id = record_id;
        this.admin_id = admin_id;
        this.amound = amound;
    }

    public Integer getPenalty_id() {
        return penalty_id;
    }

    public void setPenalty_id(Integer penalty_id) {
        this.penalty_id = penalty_id;
    }

    public Integer getCard_id() {
        return card_id;
    }

    public void setCard_id(Integer card_id) {
        this.card_id = card_id;
    }

    public Integer getRecord_id() {
        return record_id;
    }

    public void setRecord_id(Integer record_id) {
        this.record_id = record_id;
    }

    public Integer getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(Integer admin_id) {
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
