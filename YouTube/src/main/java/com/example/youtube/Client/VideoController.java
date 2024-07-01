package com.example.youtube.Client;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class VideoController {

    @FXML
    private TextField searchField;

    @FXML
    private Label titleLabel;

    @FXML
    private Label video;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Button likeButton;

    @FXML
    private Button subscribe;

    @FXML
    private Button dislikeButton;

    @FXML
    private Button addToWatchLaterButton;

    @FXML
    private Button channelButton;

    @FXML
    private VBox commentsBox;

    @FXML
    private TextArea commentInput;

    public void initialize() {
        titleLabel.setText("Video Title");
        descriptionLabel.setText("Video Description");

        likeButton.setOnAction(event -> handleLike());
        dislikeButton.setOnAction(event -> handleDislike());
        addToWatchLaterButton.setOnAction(event -> handleAddToWatchLater());
        channelButton.setOnAction(event -> handleShowChannel());
        subscribe.setOnAction(event -> handleSubscribe());
    }

    @FXML
    private void handleLike() {
        System.out.println("Liked!");
    }

    @FXML
    private void handleDislike() {
        System.out.println("Disliked!");
    }

    @FXML
    private void handleAddToWatchLater() {
        System.out.println("Added to Watch Later!");
    }

    @FXML
    private void handleShowChannel() {
        System.out.println("Show Channel!");
    }
    @FXML
    private void handleSubscribe() {
        System.out.println("Subscribed!");
    }

    @FXML
    private void handlePostComment() {
        String commentText = commentInput.getText().trim();
        if (!commentText.isEmpty()) {
            Label commentLabel = new Label(commentText);
            commentLabel.setWrapText(true);
            commentLabel.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            commentsBox.getChildren().add(commentLabel);
            commentInput.clear();
        }
    }
    private void handleSearch() {

    }
}
