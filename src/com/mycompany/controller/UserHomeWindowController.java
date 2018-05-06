package com.mycompany.controller;

import com.mycompany.controller.services.AbstractUserService;
import com.mycompany.controller.services.BookService;
import com.mycompany.controller.services.CardService;
import com.mycompany.controller.services.WindowsUtil;
import com.mycompany.controller.services.impl.BookServiceImpl;
import com.mycompany.controller.services.impl.CardServiceImpl;
import com.mycompany.controller.services.impl.UserServiceImpl;
import com.mycompany.model.bean.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private Card card;
    private Record record;
    private BookService bookService = new BookServiceImpl();
    private UserServiceImpl userService = new UserServiceImpl();
    private CardService cardService = new CardServiceImpl();

    private ObservableList<Book> bookData;
    private ObservableList<Book> borrowedBookData;
    @FXML private Button logoutButton;
    @FXML private Button searchButton;
    @FXML private Button infoButton;
    @FXML private Button getCardButton;
    @FXML private Button borrowButton;
    @FXML private Button showRecordButton;
    @FXML private Button returnBookButton;

    @FXML private TextField searchField;
    @FXML private Label label = new Label();
    @FXML private TableView<Book> tableView;
    @FXML private TableView<Book> borrowTableView;
    @FXML private TableColumn<Book, Integer> columnBookID;
    @FXML private TableColumn<Book, Integer> columnTitle;
    @FXML private TableColumn<Book, Integer> columnAuthor;
    @FXML private TableColumn<Book, Double> columnPrice;
    @FXML private TableColumn<Book, Integer> columnQuantity;
    @FXML private TableColumn<Book, Integer> b_columnBookID;
    @FXML private TableColumn<Book, Integer> b_columnTitle;
    @FXML private TableColumn<Book, Integer> b_columnAuthor;
    @FXML private TableColumn<Book, Double> b_columnPrice;
    @FXML private TableColumn<Book, Integer> b_columnQuantity;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
    protected void setUser(AbstractUser u){
        this.u = (User) u;
        label.setText(label.getText()+": "+u.getAccount());
    }
    protected void setCard(Card card){
        this.card = card;
    }


    private void setTableCell(){
        columnBookID.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        b_columnBookID.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        b_columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        b_columnAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        b_columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        b_columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    /**
     * 用搜索结果填充表格
     * */
    private void loadSearchResult(){
        tableView.getItems().clear(); // 先清空表格，再添加数据
        String title = searchField.getText();
        List<Book> bookList;
        try{
            if(title.isEmpty())
                bookList = bookService.selectAllBooks();
            else {
                bookList = bookService.findByTitle(title);
            }
            bookData.setAll(bookList);
            tableView.setItems(bookData);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 当前用户借的所有书的信息，填充表格
     * @param u 传入用户对象
     * */
    public void loadBorrowedBooks(AbstractUser u) {
        borrowTableView.getItems().clear();
        List<Book> bookList;
        try {
            bookList = bookService.selectBorrowedBooksByUser((User) u);
            borrowedBookData.setAll(bookList);
            borrowTableView.setItems(borrowedBookData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开二维码支付窗口
     * */
    private void openQRCodeWindow() throws IOException {
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

    /**
     * 当用户点击个人信息按钮，跳转到用户信息界面
     * */
    @FXML
    private void infoButtonOnClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Windows.USER_INFO_WINDOW.getValue()));
        Parent root = loader.load();
        UserInfoWindowController uc = loader.getController();
        uc.setUser(u); // 设置用户登录窗口中的User对象，以此来传递数据
        Scene home_page_scene = new Scene(root);
        Stage main_window = new Stage();
        main_window.setScene(home_page_scene);
        main_window.initModality(Modality.APPLICATION_MODAL);
        main_window.initOwner(infoButton.getScene().getWindow());
        main_window.setTitle("线上图书管系统");
        main_window.setResizable(false);
        main_window.showAndWait();
    }

    /**
     * 当用户点击申请借书卡按钮，先判断该用户是否已经拥有借书卡，如果没有的话跳转到二维码支付页面
     * */
    @FXML
    private void getCardButtonOnClick(ActionEvent event) throws IOException {
        if(u.getCard_id()!=0){
            WindowsUtil.showAlert(Alert.AlertType.INFORMATION,"已经拥有一张借书卡！");
            return;
        }
        openQRCodeWindow();
    }

    /**
     * 当点下借阅按钮的时候会做3件事：
     * 1. 检查用户是否选择了一本书
     * 2. 检查书的数量，如果数量<=0，提示数量不足
     * 3. 检查用户是否有借书卡，如果没有，提示申请借书卡
     * 4. 如果数量>0，创建Record对象，提供卡号card_id，书号book_id,当前时间borrow_time，返回时间+7。
     * 5. 书的数量-1。
     * */
    @FXML
    private void borrowButtonOnClick(ActionEvent event) {
        Book book = tableView.getSelectionModel().getSelectedItem();
        if(book==null){
            WindowsUtil.showAlert(Alert.AlertType.WARNING,"请选择一本书");
            return;
        }
        try {
            Integer qty = bookService.getQuantityByBookId(book.getBook_id());
            if(qty==null) // 如果该book_id不存在直接退出【不太可能发生】
                return;
            if(qty<=0) { // 如果数量<=0 不允许借书
                WindowsUtil.showAlert(Alert.AlertType.WARNING, "该书数量不足");
                return;
            }
            if(u.getCard_id()==0){ // 如果没有借书卡 不允许借书
                WindowsUtil.showAlert(Alert.AlertType.WARNING,"必须申请借书卡才能借书");
                return;
            }
            card = cardService.getCardById(u.getCard_id());
            Boolean b = userService.borrowBook(u, card, book);
            if(b==false){
                WindowsUtil.showAlert(Alert.AlertType.WARNING,"已经超过借书额度上限");
                return;
            }
            tableView.refresh();
            borrowedBookData.add(book);
            borrowTableView.refresh();
            System.out.println("开始借书");
            System.out.println(u);
            System.out.println(card);
            System.out.println(book);
            System.out.println(b);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(book);
    }

    @FXML
    private void showRecordButtonOnClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Windows.RECORD_WINDOW.getValue()));
        Parent root = loader.load();
        RecordWindowController rc = loader.getController();
        rc.loadRecordTable(card); // 设置用户登录窗口中的User对象，以此来传递数据
        Scene home_page_scene = new Scene(root);
        Stage main_window = new Stage();
        main_window.setScene(home_page_scene);
        main_window.initModality(Modality.APPLICATION_MODAL);
        main_window.initOwner(showRecordButton.getScene().getWindow());
        main_window.setTitle("线上图书管系统");
        main_window.setResizable(false);
        main_window.showAndWait();
    }

    public void returnBookButtonOnClick(ActionEvent event) {
    }
}
