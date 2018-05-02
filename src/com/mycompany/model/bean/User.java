package com.mycompany.model.bean;

public class User extends AbstractUser{
    private Integer userId;
    private Integer card_id;
    private Integer type_id;

    public User() {
    }

    public User(String account, String name, String password, Integer userId, Integer card_id, Integer type_id) {
        super(account, name, password);
        this.userId = userId;
        this.card_id = card_id;
        this.type_id = type_id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getCard_id() {
        return card_id;
    }

    public Integer getType_id() {
        return type_id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
        sb.append("userId=").append(userId);
        sb.append(", account='").append(this.getAccount()).append('\'');
        sb.append(", name='").append(this.getName()).append('\'');
        sb.append(", password='").append(this.getPassword()).append('\'');
        sb.append(", card_id=").append(card_id);
        sb.append(", type_id=").append(type_id);
        sb.append('}');
        return sb.toString();
    }
}
