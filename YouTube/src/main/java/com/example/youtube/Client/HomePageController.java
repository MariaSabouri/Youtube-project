package com.example.youtube.Client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
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

    @FXML
    private FlowPane videoFlowPane;
    private static Node source;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeButton.setOnAction(event -> {handlehomeButton();
            source = (Node) event.getSource();});
        yourChannel.setOnAction(event -> {handleYourChannel();
            source = (Node) event.getSource();});
        SearchButton.setOnAction(event -> {
            handleSearch();
            source = (Node) event.getSource();
        });
        for (int i = 1; i <= 8; i++) {
            Image image = new Image("D:\\Maria\\term4\\computer science\\java_programing\\HW\\Youtube-project\\YouTube\\src\\main\\resources\\com\\example\\youtube\\pic.jpg");
            ImageView imageView = new ImageView(image);
//            Button videoButton = new Button("Video " + i);
//            videoButton.setStyle("-fx-min-width: 150px;");
//            videoFlowPane.getChildren().add(videoButton);
            imageView.setFitWidth(100); // Set the width
            imageView.setFitHeight(50);
            Button button = new Button();
            button.setGraphic(new ImageView(image));
            button.setPrefWidth(100); // Preferred width
            button.setPrefHeight(50);
            videoFlowPane.getChildren().add(button);
        }

    }

    @FXML
    private void handleYourChannel() {
        stage = (Stage) yourChannel.getScene().getWindow();
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
