package com.mycompany.controller;

public enum Windows {
    MAIN_WINDOW("../view/MainWindow.fxml"),
    MANAGER_HOME_WINDOW("../view/ManagerHomeWindow.fxml"),
    USER_HOME_WINDOW("../view/UserHomeWindow.fxml"),
    REGISTER_WINDOW("../view/RegisterWindow.fxml");
    private String value;


    Windows(String s) {
        this.value = s;
    }


    public String getValue(){
        return this.value;
    }
}
