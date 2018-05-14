package com.mycompany.controller;

import appcontext.ApplicationContext;
import com.mycompany.controller.services.AbstractUserService;
import com.mycompany.controller.services.WindowsUtil;
import com.mycompany.controller.services.impl.UserServiceImpl;
import com.mycompany.model.bean.Card;
import com.mycompany.model.bean.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * 这是显示用户个人信息的窗口
 * */
public class UserInfoWindowController implements Initializable{
    User user;
    private UserServiceImpl userService = (UserServiceImpl) ApplicationContext.getBean("UserService");//= new UserServiceImpl();

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

    private ObservableList<Card> cardObservableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void saveNameOnClick(ActionEvent event){
        String name = nameField.getText();
        if(name.isEmpty())
            WindowsUtil.showAlert(Alert.AlertType.WARNING, "姓名不能为空");
        else{
            try {
                userService.rename(user,name);
                WindowsUtil.showAlert(Alert.AlertType.INFORMATION, "姓名成功修改成："+nameField.getText());
                userNameLabel.setText("姓名："+name);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void confirmButtonOnClick(ActionEvent event){
        Stage main_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_window.close();
    }

    @FXML
    private void savePwdOnClick(ActionEvent event){
        String password = pwdField.getText();
        if(password.isEmpty())
            WindowsUtil.showAlert(Alert.AlertType.WARNING, "密码不能为空");
        else{
            try {
                userService.changePwd(user,password);
                WindowsUtil.showAlert(Alert.AlertType.INFORMATION, "密码成功修改成："+pwdField.getText());
                userPasswordLabel.setText("用户密码："+password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void setUser(User u){
        this.user = u;
        System.out.println(this.user);
        userIdLabel.setText(userIdLabel.getText()+(u.getUser_id()));
        userTypeLabel.setText(userTypeLabel.getText()+(u.getType_id()==1?"学生":"教师"));
        userAccountLabel.setText(userAccountLabel.getText()+u.getAccount());
        userNameLabel.setText(userNameLabel.getText()+(u.getName()==null?"":u.getName()));
        userPasswordLabel.setText(userPasswordLabel.getText()+u.getPassword());
        userCardLabel.setText(userCardLabel.getText()+(u.getCard_id()==0?"无":u.getCard_id()));
    }
}
