package com.mycompany.controller;
import com.mycompany.controller.services.impl.UserServiceImpl;
import com.mycompany.model.bean.Card;
import com.mycompany.model.bean.Record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class RecordWindowController implements Initializable{
    private UserServiceImpl userService = new UserServiceImpl();

    @FXML private Button returnButton;
    @FXML private TableView<Record> recordTableView;
    @FXML private TableColumn<Record, Integer> record_id;
    @FXML private TableColumn<Record, Integer> card_id;
    @FXML private TableColumn<Record, Integer> book_id;
    @FXML private TableColumn<Record, String> borrow_time;
    @FXML private TableColumn<Record, String> return_time;
    private ObservableList<Record> recordData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        recordData = FXCollections.observableArrayList();
        setTableCell();
    }

    private void setTableCell() {
        record_id.setCellValueFactory(new PropertyValueFactory<>("record_id"));
        card_id.setCellValueFactory(new PropertyValueFactory<>("card_id"));
        book_id.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        borrow_time.setCellValueFactory(new PropertyValueFactory<>("borrow_time"));
        return_time.setCellValueFactory(new PropertyValueFactory<>("return_time"));
    }

    public void loadRecordTable(Card card){
        recordTableView.getItems().clear();
        try {
            List<Record> recordList = userService.getAllRecords(card);
            System.out.println("所有的记录");
            recordData.setAll(recordList);
            recordTableView.setItems(recordData);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }


    public void returnButtonOnClick(ActionEvent event) {
        Stage main_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_window.close();
    }
}
