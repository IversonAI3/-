package com.mycompany.controller;

import com.mycompany.controller.services.BookService;
import com.mycompany.controller.services.impl.BookServiceImpl;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UserHomeWindowController implements Initializable{
    private User u;
    private BookService bookService = new BookServiceImpl();
    private ObservableList<Book> bookData;
    private ObservableList<Book> borrowedBookData;
    @FXML private Button logoutButton;
    @FXML private Button searchButton;
    @FXML private Button infoButton;
    @FXML private Button getCardButton;
    @FXML private Button borrowButton;

    @FXML private TextField searchField;
    @FXML private Label label = new Label();
    @FXML private TableView<Book> tableView;
    @FXML private TableView<Book> borrowTableView;
    @FXML private TableColumn<User, Integer> columnBookID;
    @FXML private TableColumn<User, Integer> columnTitle;
    @FXML private TableColumn<User, Integer> columnAuthor;
    @FXML private TableColumn<User, Double> columnPrice;
    @FXML private TableColumn<User, Integer> columnQuantity;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(u);
        bookData = FXCollections.observableArrayList();
        borrowedBookData = FXCollections.observableArrayList();
        setTableCell();
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
        loadSearchResult();
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

    /**
     * 用搜索结果填充表格
     * */
    private void loadSearchResult(){
        tableView.getItems().clear(); // 先清空表格，再添加数据
        String title = searchField.getText();
        List<Book> bookList;
        if(title.isEmpty())
            bookList = bookService.selectAllBooks();
        else
            bookList = bookService.findByTitle(title);
        bookData.setAll(bookList);
        tableView.setItems(bookData);
    }

    /**
     * 用户借的所有书的信息填充表格
     * @param u*/
    public void loadBorrowedBooks(User u){
        borrowTableView.getItems().clear();
        try {
            List<Book> bookList = bookService.selectBorrowedBooksByUser(u);
            bookList.forEach(book -> System.out.println(book));
            borrowedBookData.setAll(bookList);
            borrowTableView.setItems(bookData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void jumpToInfoWindow(ActionEvent event, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Windows.USER_INFO_WINDOW.getValue()));
        Parent root = loader.load();
        UserInfoWindowController uc = loader.getController();
        uc.setUser(u); // 设置用户登录窗口中的User对象，以此来传递数据
        // 根据窗体视图fxml文件创建一个场景
        Scene home_page_scene = new Scene(root);
        // 通过事件来源event source得到来源所在的窗体
//        Stage main_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Stage main_window = new Stage();
        main_window.setScene(home_page_scene);
        main_window.initModality(Modality.APPLICATION_MODAL);
        main_window.initOwner(infoButton.getScene().getWindow());
        main_window.setTitle("线上图书管系统");
        main_window.setResizable(false);
        main_window.showAndWait();
    }

    private void openQRCodeWindow(ActionEvent event, User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Windows.GET_CARD_WINDOW.getValue()));
        Parent root = loader.load();
        GetCardWindowController gc = loader.getController();
        gc.setUser(u); // 设置用户登录窗口中的User对象，以此来传递数据
        Scene home_page_scene = new Scene(root);
        Stage main_window = new Stage();
        main_window.setScene(home_page_scene);
        main_window.initModality(Modality.APPLICATION_MODAL);
        main_window.initOwner(infoButton.getScene().getWindow());
        main_window.setTitle("线上图书管系统");
        main_window.setResizable(false);
        main_window.showAndWait();
    }

    @FXML
    private void infoButtonOnClick(ActionEvent event) throws IOException {
        jumpToInfoWindow(event, u);
    }

    @FXML
    private void getCardButtonOnClick(ActionEvent event) throws IOException {
        if(u.getCard_id()!=0){
            showAlert(Alert.AlertType.INFORMATION,"已经拥有一张借书卡！");
            return;
        }
        openQRCodeWindow(event, u);
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

    @FXML
    private void borrowButtonOnClick(ActionEvent event) {
        Book book = tableView.getSelectionModel().getSelectedItem();
        System.out.println(book);
    }


}
