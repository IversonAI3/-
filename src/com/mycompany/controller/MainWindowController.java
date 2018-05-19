package com.mycompany.controller;

import appcontext.ApplicationContext;
import com.mycompany.model.services.CardService;
import com.mycompany.model.services.WindowsUtil;
import com.mycompany.model.services.impl.AdminServiceImpl;
import com.mycompany.model.services.impl.UserServiceImpl;
import com.mycompany.model.bean.AbstractUser;
import com.mycompany.model.bean.Card;
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
     * 需要初始化UserServiceImpl和AdminServiceImpl来执行用户登录的操作
     * */
    private static UserServiceImpl userService = (UserServiceImpl) ApplicationContext.getBean("UserService");// = new UserServiceImpl();
    private static AdminServiceImpl adminService = (AdminServiceImpl) ApplicationContext.getBean("AdminService");// = new AdminServiceImpl();
    private static CardService cardService = (CardService) ApplicationContext.getBean("CardService");// = new CardServiceImpl();

    /**
     * 在程序开始之后做一些初始化工作
     * */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userTypeComboBox.setItems(list); // 设置下拉列表的选项
    }
    // ====================主窗体中的组件的监听器方法，当监听到事件时调用这些方法====================
    /**
     * 这是当用户点击登录按钮时监听器调用的方法
     * 执行两个操作：1.检查是用户还是管理员。 2.检查账户是否存在并验证密码
     * */
    @FXML
    private void loginButtonClicked(ActionEvent event) throws IOException, SQLException {
        // 调用UserServiceImple的登录方法
        String account = accountText.getText();
        String password = pwdText.getText();
        String userType = userTypeComboBox.getSelectionModel().getSelectedItem();
        if(userType==null){
            WindowsUtil.showAlert(Alert.AlertType.WARNING,"请选择用户类型");
            return;
        }else if(account.isEmpty() || password.isEmpty()) { // 如果没有输入密码或者账号
            WindowsUtil.showAlert(Alert.AlertType.WARNING, "请输入账号密码");
            return;
        }
        verifyAccount(event, account, password);
    }

    /**
     * 验证账号和密码,如果成功，直接跳转到对应的用户主界面或者管理员主界面
     * */
    private void verifyAccount(ActionEvent event,String account, String pwd) throws SQLException, IOException {
        AbstractUser user;
        if(isUser()){ // 如果是普通用户
            if((user = userService.findByAccountAndPassword(account, pwd))==null) // 如果账号密码与数据库中不匹配
                WindowsUtil.showAlert(Alert.AlertType.WARNING,"账号或密码错误");
            else
                jumpToUserHomePage(event, user, Windows.USER_HOME_WINDOW);
        }else{ // 如果是管理员用户
            if((user = adminService.findByAccountAndPassword(account,pwd))==null)
                WindowsUtil.showAlert(Alert.AlertType.WARNING,"账号或密码错误");
            else
                jumpToUserHomePage(event,user,Windows.MANAGER_HOME_WINDOW);

        }
    }

    /**
     * 这是当用户点击注册按钮时监听器调用的方法
     * */
    @FXML
    private void registerButtonClicked(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource(Windows.REGISTER_WINDOW.getValue()));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * 这是当用户选择下拉列表中某一个选项时调用的方法
     * */
    @FXML
    private String comboChanged(){
        System.out.println(userTypeComboBox.getSelectionModel().getSelectedItem());
        return userTypeComboBox.getSelectionModel().getSelectedItem();
    }

    /**
     * 跳到用户主界面
     * */
    private void jumpToUserHomePage(ActionEvent event, AbstractUser u, Windows window) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(window.getValue()));
        Parent root = loader.load();
        if(window.equals(Windows.USER_HOME_WINDOW)){ // 如果是跳转到普通用户主界面
            UserHomeWindowController uc = loader.getController();
            uc.setUser(u); // 设置用户登录窗口中的User对象，将这个对象数据传递给个登录后的窗口
            int card_id = ((User) u).getCard_id();
            if(card_id!=0){ // 如果用户的借书卡id值不为0（也就是不为null）
                Card card = cardService.getCardById(card_id);
                System.out.println(card);
                uc.setCard(card); // 将借书卡也一同传入下一个窗口
            }
            uc.loadBorrowedBooks(u);
        }else { // 如果跳转到管理员主界面
            ManagerHomeWindowController mc = loader.getController();
        }
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
     * 检查用户登录时是否选择普通用户
     * */
    private boolean isUser(){
        if(userTypeComboBox.getSelectionModel().getSelectedItem().equals("普通用户"))
            return true;
        else
            return false;
    }
}
