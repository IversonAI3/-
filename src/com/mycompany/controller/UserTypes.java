package com.mycompany.controller;

public enum UserTypes {
    USER("普通用户"), ADMIN("管理员用户");

    private String value;
    // 默认私有的
    UserTypes(String s) {
        this.value = s;
    }

    public String getValue(){
        return this.value;
    }
}
