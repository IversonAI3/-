package com.mycompany.model.bean;

public class User extends AbstractUser{
    private Integer user_id;
    private Integer card_id;
    private Integer type_id;

    public User() {
    }

    public User(String account, String name, String password, String regitime, Integer user_id, Integer card_id, Integer type_id) {
        super(account, name, password, regitime);
        this.user_id = user_id;
        this.card_id = card_id;
        this.type_id = type_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public Integer getCard_id() {
        return card_id;
    }

    public Integer getType_id() {
        return type_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public void setCard_id(Integer card_id) {
        this.card_id = card_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("user_id=").append(user_id);
        sb.append(", account='").append(this.getAccount()).append('\'');
        sb.append(", name='").append(this.getName()).append('\'');
        sb.append(", password='").append(this.getPassword()).append('\'');
        sb.append(", regitime='").append(this.getRegitime()).append("\'");
        sb.append(", card_id=").append(card_id);
        sb.append(", type_id=").append(type_id);
        sb.append('}');
        return sb.toString();
    }
}
