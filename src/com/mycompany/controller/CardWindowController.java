package com.mycompany.controller;

import com.mycompany.controller.services.CardService;
import com.mycompany.controller.services.impl.CardServiceImpl;
import com.mycompany.model.bean.Card;
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
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;


public class CardWindowController implements Initializable{
    private Card card;

    @FXML private Button returnButton;
    @FXML private TableView<Card> cardTableView;
    @FXML private TableColumn<Card, Integer> column_card_id;
    @FXML private TableColumn<Card, Double> column_balance;
    @FXML private TableColumn<Card, Short> column_quota;
    private ObservableList<Card> cardObservableList;

    private void setTableCell(){
        column_card_id.setCellValueFactory(new PropertyValueFactory<>("card_id"));
        column_balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        column_quota.setCellValueFactory(new PropertyValueFactory<>("quota"));
    }

    public void setCard(Card card) {
        this.card = card;
        loadCard(card);
    }

    @FXML
    private void returnButtonOnClick(ActionEvent event){
        Stage main_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_window.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cardObservableList = FXCollections.observableArrayList();
        setTableCell();
    }

    public void loadCard(Card c) {
        cardTableView.getItems().clear();
        List<Card> cardList = new LinkedList<>();
        System.out.println(c);
        cardList.add(c);
        cardObservableList.setAll(cardList);
        cardTableView.setItems(cardObservableList);
    }
}
