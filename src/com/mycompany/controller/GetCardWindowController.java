package com.mycompany.controller;

import com.mycompany.controller.services.CardService;
import com.mycompany.controller.services.impl.CardServiceImpl;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GetCardWindowController implements Initializable{
    User u;
    private CardService cardService = new CardServiceImpl();
    private UserServiceImpl userService = new UserServiceImpl();
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
            u = userService.getNewCard(u, cardService.createCard());
            uc.setUser(u); // 设置用户登录窗口中的User对象，以此来传递数据
            showAlert(Alert.AlertType.INFORMATION, "成功申请借书卡："+u.getCard_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Stage main_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_window.close();
    }

    public void setUser(User u) {
        this.u = u;
    }

    private void showAlert(Alert.AlertType alertType, String message){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setResizable(false);
        alert.getDialogPane().setPrefSize(300,100);
        alert.showAndWait();
    }
}
