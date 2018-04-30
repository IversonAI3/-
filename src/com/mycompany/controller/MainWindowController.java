package com.mycompany.controller;

import com.mycompany.controller.services.impl.UserServiceImpl;
import javafx.application.Application;
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
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/* MainController 对应 Main：程序的入口和主窗口 */
public class MainWindowController extends Application implements Initializable{

    /**
     * 不同的View的路径
     * MAIN_WINDOW：主窗口
     * USER_HOME_WINDOW_XML：普通用户登录后的主界面
     * MANAGER_HOME_WINDOW_XML：管理员用户登陆后的主界面
     * */
    private static final String MAIN_WINDOW = "../view/MainWindow.fxml";
    private static final String USER_HOME_WINDOW_XML = "../view/UserHomeWindow.fxml";
    private static final String MANAGER_HOME_WINDOW_XML= "../view/ManagerHomeWindow.fxml";

    /**
     * 主窗体中的4个组件。变量名必须和fxml文件中对应元素的id属性一致。
     * loginButton：登录按钮
     * registerButton：注册按钮
     * userTypeComboBox：用户类型下拉列表
     * errorMessage：错误提示文本
     * */
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private ComboBox<String> userTypeComboBox = new ComboBox<>();
    @FXML private Label errorMessage = new Label();

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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        errorMessage.setVisible(false);
        System.out.println("加载成功->显示主窗口...");
        Parent root = FXMLLoader.load(getClass().getResource(MAIN_WINDOW));
        primaryStage.setTitle("在线图书管理系统");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
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
            if(userTypeComboBox.getSelectionModel().getSelectedItem().equals("普通用户"))
                jumpToUserHomePage(event);
            else if(userTypeComboBox.getSelectionModel().getSelectedItem().equals("管理员用户"))
                jumpToManagerHomePage(event);
            else {
                errorMessage.setText("请选择用户类型");
                errorMessage.setVisible(true);
            }
        }catch (NullPointerException e){
            errorMessage.setText("请选择用户类型");
            errorMessage.setVisible(true);
        }
    }

    /**
     * 这是当用户点击注册按钮时监听器调用的方法
     * */
    public void registerButtonClicked(ActionEvent event){
        // 调用UserServiceImpl的注册功能

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
        Parent root = FXMLLoader.load(getClass().getResource(USER_HOME_WINDOW_XML));
        // 根据窗体视图fxml文件创建一个场景
        Scene home_page_scene = new Scene(root);
        // 通过事件来源event source得到来源所在的窗体
        Stage main_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_window.setScene(home_page_scene);
        main_window.setTitle("欢迎用户: ");
        main_window.show();
    }

    private void jumpToManagerHomePage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(MANAGER_HOME_WINDOW_XML));
        // 根据窗体视图fxml文件创建一个场景
        Scene home_page_scene = new Scene(root);
        // 通过事件来源event source得到来源所在的窗体
        Stage main_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_window.setScene(home_page_scene);
        main_window.setTitle("欢迎管理员");
        main_window.show();
    }

}
