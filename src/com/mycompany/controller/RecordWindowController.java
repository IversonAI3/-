package com.mycompany.controller;

import appcontext.ApplicationContext;
import com.mycompany.controller.services.impl.AdminServiceImpl;
import com.mycompany.model.bean.BorrowDetail;
import com.mycompany.model.bean.BorrowRecord;
import com.mycompany.model.bean.ReturnRecord;
import com.mycompany.model.bean.User;
import com.mycompany.model.dao.BorrowRecordDao;
import com.mycompany.model.dao.impl.BorrowRecordDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class RecordWindowController implements Initializable{
    private AdminServiceImpl adminService = (AdminServiceImpl) ApplicationContext.getBean("AdminService");//= new AdminServiceImpl();

    @FXML private Button returnButton;
    @FXML private MenuButton menuButton;
    @FXML private MenuItem showReturned;
    @FXML private MenuItem showUnReturned;
    @FXML private MenuItem showAll;

    private ObservableList<BorrowRecord> borrowRecordObservableList;
    @FXML private TableView<BorrowRecord> borrowRecordTableView;
    @FXML private TableColumn<BorrowRecord, Integer> b_book_id;
    @FXML private TableColumn<BorrowRecord, Integer> b_card_id;
    @FXML private TableColumn<BorrowRecord, String> b_borrow_time;
    @FXML private TableColumn<BorrowRecord, String> b_return_time;

    private ObservableList<ReturnRecord> returnRecordObservableList;
    @FXML private TableView<ReturnRecord> returnRecordTableView;
    @FXML private TableColumn<ReturnRecord, Integer> r_return_id;
    @FXML private TableColumn<ReturnRecord, Integer> r_book_id;
    @FXML private TableColumn<ReturnRecord, Integer> r_card_id;
    @FXML private TableColumn<ReturnRecord, String> r_borrow_time;
    @FXML private TableColumn<ReturnRecord, String> r_return_time;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        borrowRecordObservableList = FXCollections.observableArrayList();
        returnRecordObservableList = FXCollections.observableArrayList();
        setTableCell();
    }

    private void setTableCell(){
        b_book_id.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        b_card_id.setCellValueFactory(new PropertyValueFactory<>("card_id"));
        b_borrow_time.setCellValueFactory(new PropertyValueFactory<>("borrow_time"));
        b_return_time.setCellValueFactory(new PropertyValueFactory<>("return_time"));
        r_return_id.setCellValueFactory(new PropertyValueFactory<>("return_id"));
        r_book_id.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        r_card_id.setCellValueFactory(new PropertyValueFactory<>("card_id"));
        r_borrow_time.setCellValueFactory(new PropertyValueFactory<>("borrow_time"));
        r_return_time.setCellValueFactory(new PropertyValueFactory<>("return_time"));
    }
    @FXML
    private void returnButtonOnClick(ActionEvent event){
        Stage main_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_window.close();
    }

    // 显示所有已经归还的借书记录
    @FXML
    private void showReturnedOnClick(ActionEvent event){
        borrowRecordTableView.getItems().clear();
        try {
            List<BorrowRecord> returnedRecords = adminService.showAllReturnedRecords();
            borrowRecordObservableList.setAll(returnedRecords);
            borrowRecordTableView.setItems(borrowRecordObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // 显示所有尚未归还的借书记录
    @FXML
    private void showUnReturnedOnClick(ActionEvent event){
        borrowRecordTableView.getItems().clear();
        try {
            List<BorrowRecord> unreturnedRecords = adminService.showAllUnReturnedRecords();
            borrowRecordObservableList.setAll(unreturnedRecords);
            borrowRecordTableView.setItems(borrowRecordObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 显示所有的借书记录
    @FXML
    private void showAllOnClick(ActionEvent event){
        loadAllBorrowRecords();
    }


    public void loadAllBorrowRecords(){
        borrowRecordTableView.getItems().clear();
        List<BorrowRecord> borrowRecords;
        try {
            borrowRecords = adminService.showAllBorrowRecords();
            borrowRecordObservableList.setAll(borrowRecords);
            borrowRecordTableView.setItems(borrowRecordObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadAllReturnRecords(){
        returnRecordTableView.getItems().clear();
        List<ReturnRecord> returnRecords;
        try {
            returnRecords = adminService.showAllReturnRecords();
            returnRecordObservableList.setAll(returnRecords);
            returnRecordTableView.setItems(returnRecordObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
