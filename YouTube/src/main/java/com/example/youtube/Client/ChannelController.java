package com.example.youtube.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class ChannelController {

    @FXML
    private TextField searchField;

    @FXML
    private Button homeButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button subscribeButton;

    @FXML
    private Button subscriptionButton;

    @FXML
    private Label description;

    @FXML
    private VBox videoPane;

    @FXML
    private Label channelNameLabel;

    @FXML
    private Label subscribersLabel;

    @FXML
    private Label videosLabel;

    @FXML
    private ScrollPane videoScrollPane;

    @FXML
    private FlowPane videoFlowPane;

    @FXML
    private void initialize() {
        channelNameLabel.setText("Channel Name");
        subscribersLabel.setText("Subscribers: 10K");
        videosLabel.setText("Videos: 150");

        for (int i = 1; i <= 8; i++) {
            Image image = new Image(String.valueOf(getClass().getResource("/com/example/youtube/ProjectPlans/pic.jpg")));
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


}
