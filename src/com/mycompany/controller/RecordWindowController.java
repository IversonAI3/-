package com.mycompany.controller;

import com.mycompany.model.bean.BorrowRecord;
import com.mycompany.model.bean.ReturnRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RecordWindowController implements Initializable{
    @FXML private Button returnButton;
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

    public void loadAllBorrowRecords(){

    }

    public void loadAllReturnRecords(){

    }
}
