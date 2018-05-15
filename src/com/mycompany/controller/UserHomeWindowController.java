package com.mycompany.controller;

import appcontext.ApplicationContext;
import com.mycompany.controller.services.AbstractUserService;
import com.mycompany.controller.services.BookService;
import com.mycompany.controller.services.CardService;
import com.mycompany.controller.services.WindowsUtil;
import com.mycompany.controller.services.impl.BookServiceImpl;
import com.mycompany.controller.services.impl.CardServiceImpl;
import com.mycompany.controller.services.impl.UserServiceImpl;
import com.mycompany.model.bean.*;
import com.mycompany.model.dao.TimestampUtils;
import com.sun.prism.impl.Disposer;
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
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class UserHomeWindowController implements Initializable{
    private static User u;
    private static Card card;
    private BorrowRecord borrowRecord;
    private BookService bookService = (BookService) ApplicationContext.getBean("BookService");// = new BookServiceImpl();
    private UserServiceImpl userService = (UserServiceImpl) ApplicationContext.getBean("UserService");// = new UserServiceImpl();
    private CardService cardService = (CardService) ApplicationContext.getBean("CardService");// = new CardServiceImpl();

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    public void setCardService(CardService cardService) {
        this.cardService = cardService;
    }

    private ObservableList<Book> bookData;
    private ObservableList<BorrowDetail> borrowedBookData;
    @FXML private Button logoutButton;
    @FXML private Button searchButton;
    @FXML private Button infoButton;
    @FXML private Button getCardButton;
    @FXML private Button borrowButton;
    @FXML private Button returnButton;
    @FXML private Button showPenaltyButton;

    @FXML private TextField searchField;
    @FXML private Label label = new Label();
    @FXML private TableView<Book> tableView;
    @FXML private TableView<BorrowDetail> borrowTableView;
    @FXML private TableColumn<Book, Integer> columnBookID;
    @FXML private TableColumn<Book, Integer> columnTitle;
    @FXML private TableColumn<Book, Integer> columnAuthor;
    @FXML private TableColumn<Book, Double> columnPrice;
    @FXML private TableColumn<Book, Integer> columnQuantity;
    @FXML private TableColumn<BorrowDetail, Integer> b_columnBookID;
    @FXML private TableColumn<BorrowDetail, Integer> b_columnTitle;
    @FXML private TableColumn<BorrowDetail, Integer> b_columnAuthor;
    @FXML private TableColumn<BorrowDetail, Double> b_columnPrice;
    @FXML private TableColumn<BorrowDetail, String> b_columnBorrowTime;
    @FXML private TableColumn<BorrowDetail, String> b_columnReturnTime;

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
        System.out.println("借书卡设置成功: "+this.card);
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
        b_columnBorrowTime.setCellValueFactory(new PropertyValueFactory<>("borrow_time"));
        b_columnReturnTime.setCellValueFactory(new PropertyValueFactory<>("return_time"));
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
        List<BorrowDetail> bookList;
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
    private void getCardButtonOnClick(ActionEvent event) throws IOException, SQLException {
        if(u.getCard_id()!=0 && userService.checkCardByUserId(u)!=null){ // 如果此用户有借书卡的话
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Windows.CARD_WINDOW.getValue()));
            Parent root = loader.load();
            CardWindowController cc = loader.getController();
            Card c = cardService.getCardById(u.getCard_id());
            card = c;
            cc.setCard(c); // 设置借书卡窗口中的Card对象，以此来传递数据
            Scene home_page_scene = new Scene(root);
            Stage main_window = new Stage();
            main_window.setScene(home_page_scene);
            main_window.initModality(Modality.APPLICATION_MODAL);
            main_window.initOwner(infoButton.getScene().getWindow());
            main_window.setTitle("线上图书管系统");
            main_window.setResizable(false);
            main_window.showAndWait();
            return;
        }
        openQRCodeWindow();
    }

    /**
     * 当点下借阅按钮的时候会做3件事：
     * 1. 检查用户是否选择了一本书
     * 2. 检查用户是否已经在1分钟内借过这本书，提示稍后再试
     * 3. 检查书的数量，如果数量<=0，提示数量不足
     * 4. 检查用户是否有借书卡，如果没有，提示申请借书卡
     * 5. 如果数量>0，创建Record对象，提供卡号card_id，书号book_id,当前时间borrow_time，返回时间+7。
     * 6. 书的数量-1。
     * */
    @FXML
    private void borrowButtonOnClick(ActionEvent event) {
        Book book = tableView.getSelectionModel().getSelectedItem();
        if(book==null){
            WindowsUtil.showAlert(Alert.AlertType.WARNING,"请选择一本书");
            return;
        }
        if(u.getCard_id()==0){
            WindowsUtil.showAlert(Alert.AlertType.WARNING,"请先申请借书卡再借书");
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
            if(card.getBalance()<=0){
                WindowsUtil.showAlert(Alert.AlertType.WARNING,"余额不足，无法借书");
                return;
            }
            if(u.getCard_id()==0){ // 如果没有借书卡 不允许借书
                WindowsUtil.showAlert(Alert.AlertType.WARNING,"必须申请借书卡才能借书");
                return;
            }
            if(userService.checkDupilicateBorrow(card,book)!=null){
                WindowsUtil.showAlert(Alert.AlertType.WARNING,"已经借过这本书，请稍后再试");
                return;
            }
            card = cardService.getCardById(u.getCard_id());
            BorrowRecord b = userService.borrowBook(u, card, book);
            if(b==null){
                WindowsUtil.showAlert(Alert.AlertType.WARNING,"已经超过借书额度上限");
                return;
            }
            tableView.refresh();
            BorrowDetail bd = new BorrowDetail(book.getBook_id(),book.getTitle()
                    , book.getAuthor(), book.getPrice(), b.getBorrow_time(), b.getReturn_time());
            borrowedBookData.add(bd);
            borrowTableView.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(book);
    }

    @FXML
    private void returnButtonOnClick(ActionEvent event){
        BorrowDetail borrowDetail = borrowTableView.getSelectionModel().getSelectedItem();
        if(borrowDetail==null){
            WindowsUtil.showAlert(Alert.AlertType.WARNING,"请选择要还的书");
            return;
        }

        BorrowRecord borrowRecord = new BorrowRecord(borrowDetail.getBook_id(),
                card.getCard_id(), borrowDetail.getBorrow_time(), borrowDetail.getReturn_time());
        // 计算罚款金额：书价格的5%乘以天数
        String returnTime = borrowDetail.getReturn_time(); // 还书截止日期
        String returnedTime = TimestampUtils.getBorrowTime().toString(); // 实际还书日期
        System.out.println("还书截止日期:"+returnTime+" 实际还书日期:"+returnedTime);
        try {
            int days = userService.calculateDaysDiff(returnTime, returnedTime);
            System.out.println("相差天数: "+days);
            ReturnRecord returnRecord = userService.returnBook(borrowRecord);
            borrowedBookData.remove(borrowDetail);
            borrowTableView.refresh();
            if(days>0){
                Double fine = days * borrowDetail.getPrice() * 0.05;
                WindowsUtil.showAlert(Alert.AlertType.INFORMATION
                        ,"超过还书时间"+days+"天, 缴纳罚款: "+fine+"元");
                Penalty penalty = new Penalty();
                penalty.setBook_id(borrowDetail.getBook_id());
                penalty.setCard_id(card.getCard_id());
                penalty.setBorrow_time(borrowDetail.getBorrow_time());
                penalty.setReturn_id(userService.getRecentReturnId());
                penalty.setFine(fine);
                System.out.println(penalty);
                userService.createPenalty(penalty);
                if(fine>card.getBalance()){
                    userService.updateCardAmount(card.getCard_id(), 0.0);
                }else {
                    userService.updateCardAmount(card.getCard_id(),card.getBalance()-fine);
                }
            }else {
                WindowsUtil.showAlert(Alert.AlertType.INFORMATION,"还书成功!");
                tableView.refresh();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            WindowsUtil.showAlert(Alert.AlertType.WARNING,"还书失败，请稍后再试");
            return;
        }
    }

    @FXML
    private void refreshButtonOnClick(ActionEvent event){
        loadBorrowedBooks(u);
    }

    @FXML
    private void showPenaltyButtonOnClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Windows.PENALTY_WINDOW.getValue()));
        Parent root = loader.load();
        PenaltyWindowController pwc = loader.getController();
        if(card==null){
            WindowsUtil.showAlert(Alert.AlertType.INFORMATION,"无罚款记录，请先申请借书卡");
            return;
        }
        pwc.loadPenaltyByCard(card.getCard_id());
        Scene home_page_scene = new Scene(root);
        Stage main_window = new Stage();
        main_window.setScene(home_page_scene);
        main_window.initModality(Modality.APPLICATION_MODAL);
        main_window.initOwner(showPenaltyButton.getScene().getWindow());
        main_window.setTitle("线上图书管系统");
        main_window.setResizable(false);
        main_window.showAndWait();
    }
}
