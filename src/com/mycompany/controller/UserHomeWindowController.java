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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserHomeWindowController implements Initializable{
    private User u;
    private BookService bookService = new BookServiceImpl();
    private ObservableList<Book> data;

    @FXML private Button logoutButton;
    @FXML private Button searchButton;
    @FXML private Button infoButton;
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
        System.out.println("loaded");
    }

    @FXML
    private void logoutButtonOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(Windows.MAIN_WINDOW.getValue()));
        // 根据窗体视图fxml文件创建一个场景
        Scene home_page_scene = new Scene(root);
        // 通过事件来源event source得到来源所在的窗体
        Stage main_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_window.setScene(home_page_scene);
        main_window.setTitle("注册");
        main_window.show();
    }

    @FXML
    private void searchButtonOnClick(ActionEvent event){
        loadDataFromDatabase();
    }

    @FXML
    private void infoButtonOnClick(ActionEvent event){
        System.out.println("显示个人信息");
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
        List<Book> bookList =bookService.selectAllBooks();
        data.addAll(bookList);
        tableView.setItems(data);

    }
}
