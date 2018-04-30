package com.mycompany.model.bean;

/**
 * 所有用户的抽象父类
 * 定义了用户共同的属性：账号，姓名和密码
 * */
public abstract class AbstractUser {
    private String account;
    private String name;
    private String password;

    public AbstractUser(String account, String name, String password) {
        this.account = account;
        this.name = name;
        this.password = password;
    }

    public AbstractUser() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AbstractUser{");
        sb.append("account='").append(account).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
