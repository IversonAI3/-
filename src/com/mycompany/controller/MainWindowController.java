package com.mycompany.controller;

import com.mycompany.controller.services.impl.UserServiceImpl;
import com.mycompany.model.bean.User;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/* MainController 对应 Main：程序的入口和主窗口 */
public class MainWindowController implements Initializable{

    /**
     * 主窗体中的4个组件。变量名必须和fxml文件中对应元素的id属性一致。
     * loginButton：登录按钮
     * registerButton：注册按钮
     * userTypeComboBox：用户类型下拉列表
     * errorMessage：错误提示文本
     * */
    @FXML private Button loginButton = new Button();
    @FXML private Button registerButton = new Button();
    @FXML private ComboBox<String> userTypeComboBox = new ComboBox<>();
    @FXML private Label errorMessage = new Label();
    @FXML private TextField accountText = new TextField();
    @FXML private TextField pwdText = new TextField();

    /**
     * 用这个来初始化用户类型下拉列表
     * */
    private ObservableList<String> list = FXCollections.observableArrayList("普通用户","管理员用户");

    /**
     * 需要初始化一个UserServiceImpl来执行用户的操作
     * */
    private static UserServiceImpl userService;

    static {
        System.out.println("程序开始加载...");
        try {
            userService = new UserServiceImpl();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在程序开始之后做一些初始化工作
     * */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userTypeComboBox.setItems(list);
    }
    /**
     * 主窗体中的组件的监听器方法，当监听到事件时调用这些方法
     * */
    /**
     * 这是当用户点击登录按钮时监听器调用的方法
     * */
    public void loginButtonClicked(ActionEvent event) throws IOException {
        // 调用UserServiceImple的登录方法
        System.out.println("登录");
//        userService.login();
//        if(!verifyed) {
//            errorMessage.setVisible(true);
//            return;
//
//        }
        // 获得窗体视图fxml文件
        // 判断用户选择的是普通用户还是管理员用户
        try {
            if(isVerified()){
                if(isUser())
                    jumpToUserHomePage(event);
                else if (isAdmin() )
                    jumpToManagerHomePage(event);
                else
                    errorMessage.setText("请选择用户类型");
            }
        }catch (NullPointerException e){
            errorMessage.setText("请选择用户类型");
            errorMessage.setVisible(true);
        }
    }

    /**
     * 这是当用户点击注册按钮时监听器调用的方法
     * */
    public void registerButtonClicked(ActionEvent event) throws IOException {
        jumpToRegisterPage(event);
    }

    /**
     * 这是当用户选择下拉列表中某一个选项时调用的方法
     * */
    public String comboChanged(){
        System.out.println(userTypeComboBox.getSelectionModel().getSelectedItem());
        return userTypeComboBox.getSelectionModel().getSelectedItem();
    }

    /**
     * 下面两个方法用来打开新的场景
     * jumpToUserHomePage：调到用户主界面
     * jumpToManagerHomePage：调到管理员主界面
     * */
    private void jumpToUserHomePage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(Windows.USER_HOME_WINDOW.getValue()));
        // 根据窗体视图fxml文件创建一个场景
        Scene home_page_scene = new Scene(root);
        // 通过事件来源event source得到来源所在的窗体
        Stage main_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_window.setScene(home_page_scene);
        main_window.setTitle("欢迎用户: ");
        main_window.show();
    }

    private void jumpToManagerHomePage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(Windows.MANAGER_HOME_WINDOW.getValue()));
        // 根据窗体视图fxml文件创建一个场景
        Scene home_page_scene = new Scene(root);
        // 通过事件来源event source得到来源所在的窗体
        Stage main_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_window.setScene(home_page_scene);
        main_window.setTitle("欢迎管理员");
        main_window.show();
    }

    private void jumpToRegisterPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(Windows.REGISTER_WINDOW.getValue()));
        // 根据窗体视图fxml文件创建一个场景
        Scene home_page_scene = new Scene(root);
        // 通过事件来源event source得到来源所在的窗体
        Stage main_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_window.setScene(home_page_scene);
        main_window.setTitle("注册");
        main_window.show();
    }

    /**
     * 一些辅助方法
     * */
    private boolean isUser(){
        if(userTypeComboBox.getSelectionModel().getSelectedItem().equals("普通用户"))
            return true;
        else
            return false;
    }

    private boolean isAdmin(){
        if(userTypeComboBox.getSelectionModel().getSelectedItem().equals("管理员用户"))
            return true;
        else
            return false;
    }

    private boolean isVerified() {
        String account = accountText.getText();
        String password = pwdText.getText();
        if (account.isEmpty() || password.isEmpty()) {
            System.out.println("请输入正确的账号密码");
            errorMessage.setVisible(true);
            return false;
        }else {
            try {
                userService.login(new User());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(account+" "+ password);
            errorMessage.setVisible(false);
            return true;
        }
    }

}
