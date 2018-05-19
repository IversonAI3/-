package com.mycompany.model.services;

import javafx.scene.control.Alert;

/**
 * 这是一个工具类，提供了常用的窗口操作
 * */
public class WindowsUtil {

    private WindowsUtil(){}

    public static void showAlert(Alert.AlertType alertType, String message){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setResizable(false);
        alert.getDialogPane().setPrefSize(300,100);
        alert.showAndWait();
    }
}
