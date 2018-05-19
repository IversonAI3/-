package com.mycompany.controller;

import appcontext.ApplicationContext;
import com.mycompany.model.services.CardService;
import com.mycompany.model.services.WindowsUtil;
import com.mycompany.model.services.impl.UserServiceImpl;
import com.mycompany.model.bean.Card;
import com.mycompany.model.bean.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * 用户申请借书卡并付款的界面
 * 模拟了一个二维码支付界面（仅显示一张二维码图片）
 * 具体功能可交给第三方API来实现
 * */
public class GetCardWindowController implements Initializable{
    User u;
    Card c;
    private CardService cardService = (CardService) ApplicationContext.getBean("CardService");//= new CardServiceImpl();
    private UserServiceImpl userService = (UserServiceImpl) ApplicationContext.getBean("UserService");//= new UserServiceImpl();

    @FXML private Button confirmPayment;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void confirmPaymentOnClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Windows.USER_HOME_WINDOW.getValue()));
        Parent root = loader.load();
        UserHomeWindowController uc = loader.getController();
        try {
            c = cardService.createCard();
            if(u.getType_id()==2) { // 如果是教师
                c.setQuota((short)10);
            }
            u = userService.getNewCard(u, c);
            userService.updateCard(c);
            uc.setUser(u); // 设置用户登录窗口中的User对象，以此来传递数据
            uc.setCard(c);
            WindowsUtil.showAlert(Alert.AlertType.INFORMATION, "成功申请借书卡："+u.getCard_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Stage main_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_window.close();
    }

    public void setUser(User u) {
        this.u = u;
    }
}
