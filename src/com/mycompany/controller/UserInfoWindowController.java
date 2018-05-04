package com.mycompany.controller;

import com.mycompany.controller.services.AbstractUserService;
import com.mycompany.controller.services.impl.UserServiceImpl;
import com.mycompany.model.bean.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * 这是显示用户个人信息的窗口
 * */
public class UserInfoWindowController implements Initializable{
    User user;
    private AbstractUserService userService;
    @FXML private Label userIdLabel;
    @FXML private Label userTypeLabel;
    @FXML private Label userAccountLabel;
    @FXML private Label userPasswordLabel;
    @FXML private Label userNameLabel;
    @FXML private Label userCardLabel;
    @FXML private Button confirmButton;
    @FXML private Button saveName;
    @FXML private Button savePwd;
    @FXML private TextField nameField;
    @FXML private TextField pwdField;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            userService = new UserServiceImpl();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveNameOnClick(ActionEvent event){
        String name = nameField.getText();
        if(name.isEmpty())
            showAlert(Alert.AlertType.WARNING, "姓名不能为空");
        else{
            try {
                userService.rename(user,name);
                showAlert(Alert.AlertType.INFORMATION, "姓名成功修改成："+nameField.getText());
                userNameLabel.setText("姓名："+name);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void confirmButtonOnClick(ActionEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource(Windows.USER_HOME_WINDOW.getValue()));
//        Parent root = loader.load();
//        UserHomeWindowController uc = loader.getController();
//        uc.setUser(user); // 设置用户登录窗口中的User对象，以此来传递数据
//        // 根据窗体视图fxml文件创建一个场景
//        Scene home_page_scene = new Scene(root);
//        // 通过事件来源event source得到来源所在的窗体
//        Stage main_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        main_window.setScene(home_page_scene);
//        main_window.setTitle("线上图书管系统");
//        main_window.setResizable(false);
//        main_window.show();
        Stage main_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_window.close();
    }

    @FXML
    private void savePwdOnClick(ActionEvent event){
        String password = pwdField.getText();
        if(password.isEmpty())
            showAlert(Alert.AlertType.WARNING, "密码不能为空");
        else{
            try {
                userService.changePwd(user,password);
                showAlert(Alert.AlertType.INFORMATION, "密码成功修改成："+pwdField.getText());
                userPasswordLabel.setText("用户密码："+password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void setUser(User u){
        this.user = u;
        System.out.println(this.user);
        userIdLabel.setText(userIdLabel.getText()+(u.getUserId()));
        userTypeLabel.setText(userTypeLabel.getText()+(u.getUserId()==1?"学生":"教师"));
        userAccountLabel.setText(userAccountLabel.getText()+u.getAccount());
        userNameLabel.setText(userNameLabel.getText()+u.getName());
        userPasswordLabel.setText(userPasswordLabel.getText()+u.getPassword());
        userCardLabel.setText(userCardLabel.getText()+u.getCard_id());
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
