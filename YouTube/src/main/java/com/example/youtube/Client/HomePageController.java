package com.example.youtube.Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    private TextField searchField;

    @FXML
    private Button homeButton;

    @FXML
    private Button yourChannel;

    @FXML
    private Button subscriptionsButton;

    @FXML
    private VBox videoListContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText();
    }


}
