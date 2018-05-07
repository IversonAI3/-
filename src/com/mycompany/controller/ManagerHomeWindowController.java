package com.mycompany.controller;

import com.mycompany.controller.services.BookService;
import com.mycompany.controller.services.WindowsUtil;
import com.mycompany.controller.services.impl.AdminServiceImpl;
import com.mycompany.controller.services.impl.BookServiceImpl;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerHomeWindowController implements Initializable{
    private BookService bookService = new BookServiceImpl();
    private AdminServiceImpl adminService = new AdminServiceImpl();
    private UserServiceImpl userService = new UserServiceImpl();

    private ObservableList<Book> bookData;
    private ObservableList<User> userData;

    @FXML private TableView<Book> bookTableView;
    @FXML private TableView<User> userTableView;

    @FXML private Button searchBookButton;
    @FXML private Button searchUserButton;

    @FXML private TextField searchBookField;
    @FXML private TextField searchUserField;

    @FXML private TextField book_title;
    @FXML private TextField book_author;
    @FXML private TextField book_price;
    @FXML private TextField book_quantity;

    @FXML private Button addBookButton;
    @FXML private Button deleteBookButton;
    @FXML private Button showBorrowRecord;
    @FXML private Button showPenaltyButton;

    @FXML private TableColumn<Book, Integer> columnBookID;
    @FXML private TableColumn<Book, String> columnTitle;
    @FXML private TableColumn<Book, String> columnAuthor;
    @FXML private TableColumn<Book, String> columnPrice;
    @FXML private TableColumn<Book, String> columnQuantity;

    @FXML private TableColumn<User, Integer> columnUserId;
    @FXML private TableColumn<User, String> columnAccount;
    @FXML private TableColumn<User, String> columnName;
    @FXML private TableColumn<User, String> columnPwd;
    @FXML private TableColumn<User, Integer> columnCardId;
    @FXML private TableColumn<User, Integer> columnUserType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookData = FXCollections.observableArrayList();
        userData = FXCollections.observableArrayList();
        setTableCell();
        bookTableView.setEditable(true);
        columnTitle.setCellFactory(TextFieldTableCell.forTableColumn());
        columnAuthor.setCellFactory(TextFieldTableCell.forTableColumn());
        columnPrice.setCellFactory(TextFieldTableCell.forTableColumn());
        columnQuantity.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    @FXML
    private void searchBookOnClick(ActionEvent event){
        loadBookData();
    }

    @FXML
    private void searchUserOnClick(ActionEvent event){
        loadUserData();
    }

    @FXML
    private void addBookButtonOnClick(ActionEvent event){
        String title = book_title.getText();
        String author = book_author.getText();
        String price = book_price.getText();
        String quantity = book_quantity.getText();
        if(title.isEmpty()||author.isEmpty()||price.isEmpty()||quantity.isEmpty()){
            WindowsUtil.showAlert(Alert.AlertType.WARNING,"请填入所有的信息");
            return;
        }
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        try{
            Double p = Double.parseDouble(price);
            Integer qty = Integer.parseInt(quantity);
            book.setPrice(p);
            book.setQuantity(qty);
        }catch (NumberFormatException ex){
            WindowsUtil.showAlert(Alert.AlertType.ERROR,"请输入正确格式的价格和数量");
        }
        if(bookData.contains(book)){
            WindowsUtil.showAlert(Alert.AlertType.WARNING,"已经存在，无法添加重复的书");
            return;
        }
        try {
            System.out.println(book);
            book = bookService.addNewBook(book);
            bookData.add(book);
            System.out.println("添加成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bookTableView.refresh();
    }

    @FXML
    private void returnButtonOnClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(Windows.MAIN_WINDOW.getValue()));
        Scene home_page_scene = new Scene(root);
        Stage main_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_window.setScene(home_page_scene);
        main_window.show();
    }

    @FXML
    private void deleteBookButtonOnClick(ActionEvent event) throws SQLException {
        Book book = bookTableView.getSelectionModel().getSelectedItem();
        if(book==null){
            WindowsUtil.showAlert(Alert.AlertType.WARNING,"请选择一本书");
            return;
        }
        if(!adminService.showAllRecordsByBook(book).isEmpty()){
            WindowsUtil.showAlert(Alert.AlertType.ERROR,"这本书正被借用，无法删除");
        }else {
            bookData.remove(book);
            adminService.deleteBookById(book);
            bookTableView.refresh();
            System.out.println("删除成功");
        }
    }

    private void setTableCell(){
        columnBookID.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnUserId.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        columnAccount.setCellValueFactory(new PropertyValueFactory<>("account"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPwd.setCellValueFactory(new PropertyValueFactory<>("password"));
        columnCardId.setCellValueFactory(new PropertyValueFactory<>("card_id"));
        columnUserType.setCellValueFactory(new PropertyValueFactory<>("type_id"));
    }

    @FXML
    private void changeTitleCellEvent(TableColumn.CellEditEvent cellEditEvent){
        Book book = bookTableView.getSelectionModel().getSelectedItem();
        book.setTitle((String) cellEditEvent.getNewValue());
    }

    @FXML
    private void changeAuthorCellEvent(TableColumn.CellEditEvent cellEditEvent){
        Book book = bookTableView.getSelectionModel().getSelectedItem();
        book.setAuthor((String) cellEditEvent.getNewValue());
        System.out.println(book);
    }

    @FXML
    private void changePriceCellEvent(TableColumn.CellEditEvent cellEditEvent){
        Book book = bookTableView.getSelectionModel().getSelectedItem();
        try {
            book.setPrice((Double) cellEditEvent.getNewValue());
            System.out.println(book);
        }catch (NumberFormatException e){
            WindowsUtil.showAlert(Alert.AlertType.WARNING,"请输入正确的价格");
        }
    }

    @FXML
    private void changeQuantityCellEvent(TableColumn.CellEditEvent cellEditEvent){
        Book book = bookTableView.getSelectionModel().getSelectedItem();
        try {
            Integer qty = (Integer) cellEditEvent.getNewValue();
            if(qty<0){
                WindowsUtil.showAlert(Alert.AlertType.WARNING,"数量不能少于0");
                return;
            }
            book.setQuantity(qty);
            System.out.println(book);
        }catch (NumberFormatException e){
            WindowsUtil.showAlert(Alert.AlertType.WARNING,"请输入正确的价格");
        }
    }

    private void loadBookData(){
        bookTableView.getItems().clear(); // 先清空表格，再添加数据
        String title = searchBookField.getText();
        List<Book> bookList;
        try{
            if(title.isEmpty())
                bookList = bookService.selectAllBooks();
            else {
                bookList = bookService.findByTitle(title);
            }
            bookData.setAll(bookList);
            bookTableView.setItems(bookData);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadUserData(){
        userTableView.getItems().clear();
        String account = searchUserField.getText();
        List<User> userList;
        try{
            if(account.isEmpty())
                userList = adminService.showAllUsers();
            else {
                userList = new LinkedList<>();
                userList.add(userService.findByAccount(account));
            }
            userData.setAll(userList);
            userTableView.setItems(userData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
