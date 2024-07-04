package com.example.youtube.Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    private Button SearchButton;

    @FXML
    private Button subscriptionsButton;

    @FXML
    private VBox videoListContainer;
    private static Stage stage;
    private static Node source;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeButton.setOnAction(event -> {handlehomeButton();
            source = (Node) event.getSource();});
        yourChannel.setOnAction(event -> {hadleYourChannel();
            source = (Node) event.getSource();});
        SearchButton.setOnAction(event -> {
            handleSearch();
            source = (Node) event.getSource();
        });


    }

    @FXML
    private void hadleYourChannel() {
        stage = (Stage) homeButton.getScene().getWindow();
        UiController.changingscene(stage,"channel-view.fxml");


    }
    @FXML
    private void handlehomeButton() {
        stage = (Stage) homeButton.getScene().getWindow();
        UiController.changingscene(stage,"login-view.fxml");
    }

    @FXML
    private void handleSearch() {
        stage = (Stage) SearchButton.getScene().getWindow();
        String searchText = searchField.getText();
        CommonTools.searchbarToll(searchText,stage);
        searchField.clear();
    }


}
