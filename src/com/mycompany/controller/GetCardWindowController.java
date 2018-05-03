package com.mycompany.controller;

import com.mycompany.model.bean.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class GetCardWindowController {
    User u;

    @FXML private Button confirmPayment;

    @FXML
    private void confirmPaymentOnClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Windows.USER_HOME_WINDOW.getValue()));
        Parent root = loader.load();
        UserHomeWindowController uc = loader.getController();
        uc.setUser(u); // 设置用户登录窗口中的User对象，以此来传递数据
        // 根据窗体视图fxml文件创建一个场景
        Scene home_page_scene = new Scene(root);
        // 通过事件来源event source得到来源所在的窗体
        Stage main_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_window.setScene(home_page_scene);
        main_window.setTitle("线上图书管系统");
        main_window.setResizable(false);
        main_window.show();
    }

    public void setUser(User u) {
        this.u = u;
    }
}
