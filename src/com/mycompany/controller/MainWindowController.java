package com.mycompany.controller;

import com.mycompany.controller.services.impl.AdminServiceImpl;
import com.mycompany.controller.services.impl.UserServiceImpl;
import com.mycompany.model.bean.AbstractUser;
import com.mycompany.model.bean.Admin;
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
import javafx.scene.control.*;
import javafx.stage.Modality;
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
    @FXML private TextField accountText = new TextField();
    @FXML private TextField pwdText = new TextField();

    /**
     * 用这个来初始化用户类型下拉列表
     * */
    private ObservableList<String> list
            = FXCollections.observableArrayList(UserTypes.USER.getValue(), UserTypes.ADMIN.getValue());

    /**
     * 需要初始化一个UserServiceImpl来执行用户的操作
     * */
    private static UserServiceImpl userService;
    private static AdminServiceImpl adminService;
    static {
        System.out.println("程序开始加载...");
        try {
            userService = new UserServiceImpl();
            adminService = new AdminServiceImpl();
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
     * 执行两个操作：1.检查是用户还是管理员。 3.检查账户是否存在并验证密码
     * */
    @FXML
    private void loginButtonClicked(ActionEvent event) throws IOException, SQLException {
        // 调用UserServiceImple的登录方法
        System.out.println("登录");
        String account = accountText.getText();
        String password = pwdText.getText();
        String userType = userTypeComboBox.getSelectionModel().getSelectedItem();
        if(userType==null){
            showAlert(Alert.AlertType.WARNING,"请选择用户类型");
            return;
        }else if(account.isEmpty() || password.isEmpty()) { // 如果没有输入密码或者账号
            showAlert(Alert.AlertType.WARNING, "请输入账号密码");
            return;
        }
        AbstractUser user;
        if(userType.equals(UserTypes.USER.getValue())){
            if((user = verifyAccount(UserTypes.USER,account,password))!=null)
                jumpToUserHomePage(event, (User) user);
            else
                showAlert(Alert.AlertType.WARNING,"账号或密码错误");
        }else{
            if((user = verifyAccount(UserTypes.ADMIN,account,password))!=null)
                jumpToManagerHomePage(event);
            else
                showAlert(Alert.AlertType.WARNING,"账号或密码错误");
        }
        /*
        * userService = new UserServiceImpl();
       String account_input = account.getText();
       String pwd_input = password.getText();
       if(account_input.isEmpty() || pwd_input.isEmpty()){
           showAlert(Alert.AlertType.ERROR, "请输入账户和密码");
           return;
       }
       User u = new User();
       u.setAccount(account_input);
       u.setPassword(pwd_input);

       if(userService.register(u)!=null){
           showAlert(Alert.AlertType.INFORMATION, "注册成功");
       }else {
           showAlert(Alert.AlertType.WARNING, "该用户名已经存在");
       }*/
    }

    private AbstractUser verifyAccount(UserTypes ut,String account, String pwd) throws SQLException {
        AbstractUser user;
        if(ut.getValue().equals("普通用户")){
            if((user = userService.findByAccountAndPassword(account, pwd))==null){
                System.out.println("账户或密码不正确");
            }
            return user;
        }else{
            user = new Admin();
            user.setAccount(account);
            user.setPassword(pwd);
            if((user = adminService.findByAccountAndPassword(account,pwd))==null){
                System.out.println("账户或密码不正确");
            }
            return user;
        }
    }


    /**
     * 这是当用户点击注册按钮时监听器调用的方法
     * */
    @FXML
    private void registerButtonClicked(ActionEvent event) throws IOException {
//        jumpToRegisterPage(event);
        handleRegisterButton(event);
    }

    /**
     * 这是当用户选择下拉列表中某一个选项时调用的方法
     * */
    @FXML
    private String comboChanged(){
        System.out.println(userTypeComboBox.getSelectionModel().getSelectedItem());
        return userTypeComboBox.getSelectionModel().getSelectedItem();
    }

    private void handleRegisterButton(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource(Windows.REGISTER_WINDOW.getValue()));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * 跳到用户主界面
     * */
    private void jumpToUserHomePage(ActionEvent event, User u) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Windows.USER_HOME_WINDOW.getValue()));
        Parent root = loader.load();
        UserHomeWindowController uc = loader.getController();
        uc.setUser(u); // 设置用户登录窗口中的User对象，以此来传递数据
        // 根据窗体视图fxml文件创建一个场景
        Scene home_page_scene = new Scene(root);
        // 通过事件来源event source得到来源所在的窗体
        Stage user_home_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        user_home_window.setScene(home_page_scene);
        user_home_window.setTitle("线上图书管系统");
        user_home_window.setResizable(false);
        user_home_window.show();
    }

    /**
     * 跳到管理员主界面
     * */
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

    /**
     * 跳到注册界面
     * */
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

    /**
     * 弹窗提示
     * @param alertType 警告类型
     * @param message 提示消息
     * */
    private void showAlert(Alert.AlertType alertType, String message){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setResizable(false);
        alert.getDialogPane().setPrefSize(300,100);
        alert.showAndWait();
    }

}
