package com.mycompany.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

public class PenaltyWindowController implements Initializable{


    @FXML private Button returnButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void returnButtonOnClick(ActionEvent event){
        Stage main_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_window.close();
    }

    public void loadPenaltyRecords(){

    }

}
