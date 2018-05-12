package com.mycompany.model.bean;

/**
 * 普通用户和管理员用户的抽象父类
 * 定义了用户共同的属性：账号，姓名，密码和注册时间
 * */
public abstract class AbstractUser {
    private String account;
    private String name;
    private String password;
    private String regitime;

    public AbstractUser() {
    }

    public AbstractUser(String account, String name, String password, String regitime) {
        this.account = account;
        this.name = name;
        this.password = password;
        this.regitime = regitime;
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

    public String getRegitime() {
        return regitime;
    }

    public void setRegitime(String regitime) {
        this.regitime = regitime;
    }
}
