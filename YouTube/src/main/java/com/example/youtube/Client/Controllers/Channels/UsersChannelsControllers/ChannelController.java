package com.example.youtube.Client.Controllers.Channels.UsersChannelsControllers;

import com.example.youtube.Client.Controllers.Channels.ChannelInterface;
import com.example.youtube.Client.Controllers.CommonTools;
import com.example.youtube.Client.UiController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;

public class ChannelController implements ChannelInterface {

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
    private static JSONObject UserInfo;
    private static String playlistName;
    private static Stage stage;
    public static void setUserInfo(JSONObject userInfo) {
        UserInfo = userInfo;
    }

    public static void setPlaylistChoosen(String id) {
        playlistName=id;
    }

    @FXML
    private void initialize() {
        channelNameLabel.setText("Channel Name");
        subscribersLabel.setText("Subscribers: ");
        searchButton.setOnAction(event -> searchButtonhandler());
        homeButton.setOnAction(event -> homeButtonhandler());

    }


    @Override
    public void homeButtonhandler() {
        stage = (Stage) homeButton.getScene().getWindow();
        UiController.changingscene(stage,"homePage-view.fxml");
    }

    @Override
    public void searchButtonhandler() {
        stage = (Stage) searchButton.getScene().getWindow();
        try {
            String searchText = searchField.getText();
            if (searchText.isEmpty()){
                throw new IllegalArgumentException("Search Textfield is empty");
            }
            CommonTools.searchbarToll(searchText,stage);
            searchField.clear();
        }catch (IllegalArgumentException e){
            CommonTools.showingError();

        }
    }
}
