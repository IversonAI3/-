package com.mycompany.controller;

import com.mycompany.controller.services.impl.AdminServiceImpl;
import com.mycompany.controller.services.impl.UserServiceImpl;
import com.mycompany.model.bean.Penalty;
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
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PenaltyWindowController implements Initializable{

    private AdminServiceImpl adminService = new AdminServiceImpl();
    private UserServiceImpl userService = new UserServiceImpl();

    @FXML private Button returnButton;
    @FXML private TableView<Penalty> penaltyTableView;
    private ObservableList<Penalty> penaltyObservableList;
    @FXML private TableColumn<Penalty, Integer> penalty_id;
    @FXML private TableColumn<Penalty, Integer> return_id;
    @FXML private TableColumn<Penalty, Integer> card_id;
    @FXML private TableColumn<Penalty, String> borrow_time;
    @FXML private TableColumn<Penalty, Integer> book_id;
    @FXML private TableColumn<Penalty, Double> fine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        penaltyObservableList = FXCollections.observableArrayList();
        setTableCell();
    }

    private void setTableCell(){
        penalty_id.setCellValueFactory(new PropertyValueFactory<>("penalty_id"));
        return_id.setCellValueFactory(new PropertyValueFactory<>("return_id"));
        card_id.setCellValueFactory(new PropertyValueFactory<>("card_id"));
        borrow_time.setCellValueFactory(new PropertyValueFactory<>("borrow_time"));
        book_id.setCellValueFactory(new PropertyValueFactory<>("book_id"));
        fine.setCellValueFactory(new PropertyValueFactory<>("fine"));
    }

    @FXML
    private void returnButtonOnClick(ActionEvent event){
        Stage main_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_window.close();
    }

    public void loadPenaltyRecords(){
        penaltyTableView.getItems().clear();
        List<Penalty> penalties;
        try {
            penalties = adminService.showAllPenalties();
            penaltyObservableList.setAll(penalties);
            penaltyTableView.setItems(penaltyObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadPenaltyByCard(Integer card_id){
        penaltyTableView.getItems().clear();
        List<Penalty> penalties;
        try {
            penalties = userService.showPenalties(card_id);
            penaltyObservableList.setAll(penalties);
            penaltyTableView.setItems(penaltyObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
