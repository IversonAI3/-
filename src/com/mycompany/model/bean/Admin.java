package com.mycompany.model.bean;

public class Admin extends AbstractUser{
    private Integer admin_id;

    public Admin(String account, String name, String password, Integer admin_id) {
        super(account, name, password);
        this.admin_id = admin_id;
    }

    public Admin() {
    }

    public Integer getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(Integer admin_id) {
        this.admin_id = admin_id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Admin{");
        sb.append("admin_id=").append(admin_id);
        sb.append('}');
        return sb.toString();
    }
}
