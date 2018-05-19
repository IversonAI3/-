package com.mycompany.controller;

import appcontext.ApplicationContext;
import com.mycompany.model.services.BookService;
import com.mycompany.model.services.WindowsUtil;
import com.mycompany.model.services.impl.AdminServiceImpl;
import com.mycompany.model.services.impl.UserServiceImpl;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerHomeWindowController implements Initializable{
    private BookService bookService = (BookService) ApplicationContext.getBean("BookService");//= new BookServiceImpl();
    private AdminServiceImpl adminService = (AdminServiceImpl) ApplicationContext.getBean("AdminService");//= new AdminServiceImpl();
    private UserServiceImpl userService = (UserServiceImpl) ApplicationContext.getBean("UserService");// = new UserServiceImpl();

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
    @FXML private Button showAllRecord;
    @FXML private Button showPenaltyButton;

    @FXML private TableColumn<Book, Integer> columnBookID;
    @FXML private TableColumn<Book, String> columnTitle;
    @FXML private TableColumn<Book, String> columnAuthor;
    @FXML private TableColumn<Book, Double> columnPrice;
    @FXML private TableColumn<Book, Integer> columnQuantity;

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
        userTableView.setEditable(true);
        columnTitle.setCellFactory(TextFieldTableCell.forTableColumn());
        columnAuthor.setCellFactory(TextFieldTableCell.forTableColumn());
        columnPrice.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        columnQuantity.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        columnName.setCellFactory(TextFieldTableCell.forTableColumn());
        columnPwd.setCellFactory(TextFieldTableCell.forTableColumn());
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
            if(p<0.0 || qty<0){
                WindowsUtil.showAlert(Alert.AlertType.WARNING,"输入的价格或数量无效");
                return;
            }
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
        try {
            bookService.updateBook(book);
            bookTableView.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void changeAuthorCellEvent(TableColumn.CellEditEvent cellEditEvent){
        Book book = bookTableView.getSelectionModel().getSelectedItem();
        book.setAuthor((String) cellEditEvent.getNewValue());
        try {
            bookService.updateBook(book);
            bookTableView.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void changePriceCellEvent(TableColumn.CellEditEvent cellEditEvent){
        Book book = bookTableView.getSelectionModel().getSelectedItem();
        try {
            Double price = Double.parseDouble(cellEditEvent.getNewValue().toString());
            if(price<0.0){
                WindowsUtil.showAlert(Alert.AlertType.WARNING,"价格不能少于0.0");
                bookTableView.refresh();
                return;
            }
            book.setPrice(price);
            System.out.println(book);
            bookService.updateBook(book);
            bookTableView.refresh();
        } catch (NumberFormatException e){
            WindowsUtil.showAlert(Alert.AlertType.WARNING,"请输入一个数值");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void changeQuantityCellEvent(TableColumn.CellEditEvent cellEditEvent){
        Book book = bookTableView.getSelectionModel().getSelectedItem();
        String newQty = cellEditEvent.getNewValue().toString();
        try {
            Integer qty = Integer.parseInt(newQty);
            if(qty<0){
                WindowsUtil.showAlert(Alert.AlertType.WARNING,"数量不能少于0");
                bookTableView.refresh();
                return;
            }
            book.setQuantity(qty);
            bookService.updateBook(book);
            bookTableView.refresh();
            System.out.println(book);
        }catch (NumberFormatException e){
            WindowsUtil.showAlert(Alert.AlertType.WARNING,"请输入一个数值 ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void changeNameCellEvent(TableColumn.CellEditEvent cellEditEvent){
        User user = userTableView.getSelectionModel().getSelectedItem();
        String newName = (String) cellEditEvent.getNewValue();
        user.setName(newName);
        try {
            userService.rename(user, newName);
            userTableView.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void changePwdCellEvent(TableColumn.CellEditEvent cellEditEvent){
        User user = userTableView.getSelectionModel().getSelectedItem();
        String newPwd = (String) cellEditEvent.getNewValue();
        user.setPassword(newPwd);
        try {
            userService.changePwd(user,newPwd);
            userTableView.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showAllRecordOnClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Windows.RECORD_WINDOW.getValue()));
        Parent root = loader.load();
        RecordWindowController rwc = loader.getController();
        rwc.loadAllBorrowRecords();
        rwc.loadAllReturnRecords();
        Scene home_page_scene = new Scene(root);
        Stage main_window = new Stage();
        main_window.setScene(home_page_scene);
        main_window.initModality(Modality.APPLICATION_MODAL);
        main_window.initOwner(showPenaltyButton.getScene().getWindow());
        main_window.setTitle("线上图书管系统");
        main_window.setResizable(false);
        main_window.showAndWait();
    }

    @FXML
    private void showPenaltyButtonOnClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Windows.PENALTY_WINDOW.getValue()));
        Parent root = loader.load();
        PenaltyWindowController pwc = loader.getController();
        pwc.loadPenaltyRecords();
        Scene home_page_scene = new Scene(root);
        Stage main_window = new Stage();
        main_window.setScene(home_page_scene);
        main_window.initModality(Modality.APPLICATION_MODAL);
        main_window.initOwner(showPenaltyButton.getScene().getWindow());
        main_window.setTitle("线上图书管系统");
        main_window.setResizable(false);
        main_window.showAndWait();
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
