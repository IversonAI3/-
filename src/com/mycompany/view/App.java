package com.mycompany.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 程序的入口
 */
public class App extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("加载成功->运行程序...");
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("在线图书管理系统");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
}
