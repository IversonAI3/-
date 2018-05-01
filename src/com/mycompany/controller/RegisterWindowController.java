package com.mycompany.controller;

import com.mycompany.controller.services.AbstractUserService;
import com.mycompany.controller.services.impl.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterWindowController implements Initializable{
    @FXML private TextField account;
    @FXML private TextField password;
    @FXML private Button registerButton;
    @FXML private Button backButton;
    @FXML private ComboBox<String> registerComboBox = new ComboBox<>();


    private ObservableList<String> list
            = FXCollections.observableArrayList(UserTypes.USER.getValue(),UserTypes.ADMIN.getValue());

    private AbstractUserService userService;

    public void backToMainWindow(ActionEvent event) throws IOException {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
//        Parent root = FXMLLoader.load(getClass().getResource(Windows.MAIN_WINDOW.getValue()));
//        // 根据窗体视图fxml文件创建一个场景
//        Scene home_page_scene = new Scene(root);
//        // 通过事件来源event source得到来源所在的窗体
//        Stage main_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        main_window.setScene(home_page_scene);
//        main_window.setTitle("注册");
//        main_window.show();
    }

    public void register(ActionEvent event) throws SQLException {
//        userService.register();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registerComboBox.setItems(list);
        try {
            userService = new UserServiceImpl();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void comboBoxOnChange(){
        String type = registerComboBox.getSelectionModel().getSelectedItem();
        System.out.println(type);
    }
}
