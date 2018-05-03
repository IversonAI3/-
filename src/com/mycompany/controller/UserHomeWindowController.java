package com.mycompany.controller;

import com.mycompany.controller.services.AbstractUserService;
import com.mycompany.controller.services.BookService;
import com.mycompany.controller.services.CardService;
import com.mycompany.controller.services.impl.BookServiceImpl;
import com.mycompany.controller.services.impl.CardServiceImpl;
import com.mycompany.controller.services.impl.UserServiceImpl;
import com.mycompany.model.bean.Book;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UserHomeWindowController implements Initializable{
    private User u;
    private BookService bookService = new BookServiceImpl();
    private CardService cardService = new CardServiceImpl();
    private UserServiceImpl userService;
    private ObservableList<Book> data;

    @FXML private Button logoutButton;
    @FXML private Button searchButton;
    @FXML private Button infoButton;
    @FXML private Button getCardButton;
    @FXML private TextField searchField;
    @FXML private Label label = new Label();
    @FXML private TableView<Book> tableView;
    @FXML private TableColumn<User, Integer> columnBookID;
    @FXML private TableColumn<User, Integer> columnTitle;
    @FXML private TableColumn<User, Integer> columnAuthor;
    @FXML private TableColumn<User, Double> columnPrice;
    @FXML private TableColumn<User, Integer> columnQuantity;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = FXCollections.observableArrayList();
        setTableCell();
        try {
            userService = new UserServiceImpl();
            System.out.println("用户主界面加载成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logoutButtonOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(Windows.MAIN_WINDOW.getValue()));
        // 根据窗体视图fxml文件创建一个场景
        Scene home_page_scene = new Scene(root);
        // 通过事件来源event source得到来源所在的窗体
        Stage main_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_window.setScene(home_page_scene);
        main_window.show();
    }

    @FXML
    private void searchButtonOnClick(ActionEvent event){
        loadDataFromDatabase();
    }

    /**
     * 这个方法用来在不同的窗口之间传递User信息
     * 注意：这个方法在initialize()方法之后被调用
     * */
    protected void setUser(User u){
        this.u = u;
        label.setText(label.getText()+": "+u.getAccount());
    }

    private void setTableCell(){
        columnBookID.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    private void loadDataFromDatabase(){
        tableView.getItems().clear(); // 先清空表格，再添加数据
        String title = searchField.getText();
        List<Book> bookList;
        if(title.isEmpty())
            bookList = bookService.selectAllBooks();
        else
            bookList = bookService.findByTitle(title);
        data.setAll(bookList);
        tableView.setItems(data);
    }

    private void jumpToInfoWindow(ActionEvent event, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Windows.USER_INFO_WINDOW.getValue()));
        Parent root = loader.load();
        UserInfoWindowController uc = loader.getController();
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
    @FXML
    private void infoButtonOnClick(ActionEvent event) throws IOException {
        jumpToInfoWindow(event, u);
    }

    @FXML
    private void getCardButtonOnClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Windows.GET_CARD_WINDOW.getValue()));
        Parent root = loader.load();
        GetCardWindowController gc = loader.getController();
        // to do 添加借书卡，并且更新数据库
        System.out.println(u);
        if(u.getCard_id()!=0){
            showAlert(Alert.AlertType.INFORMATION,"已经拥有一张借书卡！");

        }
        try {
            u = userService.getNewCard(u, cardService.createCard());
            System.out.println("获得借书卡!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        gc.setUser(u); // 设置用户登录窗口中的User对象，以此来传递数据
        System.out.println(u);
        // 根据窗体视图fxml文件创建一个场景
        Scene home_page_scene = new Scene(root);
        // 通过事件来源event source得到来源所在的窗体
        Stage main_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_window.setScene(home_page_scene);
        main_window.setTitle("线上图书管系统");
        main_window.setResizable(false);
        main_window.show();
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
