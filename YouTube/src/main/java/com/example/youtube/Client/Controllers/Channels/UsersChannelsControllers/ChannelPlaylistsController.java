package com.example.youtube.Client.Controllers.Channels.UsersChannelsControllers;

import com.example.youtube.Client.Controllers.Channels.ChannelInterface;
import com.example.youtube.Client.Controllers.CommonTools;
import com.example.youtube.Client.UiController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class ChannelPlaylistsController implements Initializable, ChannelInterface {
    @FXML
    private VBox SearchResultVbox;

    @FXML
    private Label channelNameLabel;

    @FXML
    private Button homeButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private Label subscribersLabel;

    @FXML
    private Button subscriptionButton;

    @FXML
    private Button creatPlaylist;
    private static JSONObject UserInfo;
    private static Stage stage;
    public static void setUserInfo(JSONObject userInfo) {
        UserInfo = userInfo;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeButton.setOnAction(event -> homeButtonhandler());
        searchButton.setOnAction(event -> searchButtonhandler());
        creatPlaylist.setOnAction(event -> creatPlaylisthandler());

    }

    private void creatPlaylisthandler() {
        stage=(Stage) creatPlaylist.getScene().getWindow();
        CreatePlaylist.setUserInfo(UserInfo);
        UiController.changingscene(stage,"creatPlaylist.fxml");
    }

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

    public void homeButtonhandler() {
        stage = (Stage) homeButton.getScene().getWindow();
        UiController.changingscene(stage,"homePage-view.fxml");
    }
}
