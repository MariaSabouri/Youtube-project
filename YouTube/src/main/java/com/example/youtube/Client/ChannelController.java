package com.example.youtube.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class ChannelController {

    @FXML
    private TextField searchField;

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
    private Button subscribeButton;

    @FXML
    private void initialize() {
        channelNameLabel.setText("Channel Name");
        subscribersLabel.setText("Subscribers: 10K");
        videosLabel.setText("Videos: 150");

        for (int i = 1; i <= 8; i++) {
            Button videoButton = new Button("Video " + i);
            videoButton.setStyle("-fx-min-width: 150px;");
            videoFlowPane.getChildren().add(videoButton);
        }
    }


}
