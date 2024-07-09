package com.example.youtube.Client.Controllers.Channels.ChannelsConltrollers;

import com.example.youtube.Client.Controllers.ChannelInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class VisitingChannelPlaylist implements Initializable, ChannelInterface {
    @FXML
    private Label ChannelLeterLabel1;

    @FXML
    private ImageView SearchButton;

    @FXML
    private VBox SearchResultVbox;

    @FXML
    private Button SubscribeButton;

    @FXML
    private Label UsernameLeterLabel;

    @FXML
    private Label channelNameLabel;

    @FXML
    private Button creatPlaylist;

    @FXML
    private Button homeButton;

    @FXML
    private Button home_button;

    @FXML
    private Label numOfVideosLabel;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private Label subscribersLabel;

    @FXML
    private Button subscriptionButton;

    @Override
    public void homeButtonhandler() {

    }

    @Override
    public void searchButtonhandler() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
