package com.example.youtube.Client.Controllers.SignUpLoginHomeControllers;

import com.example.youtube.Client.ClientToServerConnection;
import com.example.youtube.Client.Controllers.CommonTools;
import com.example.youtube.Client.UiController;
import com.example.youtube.Client.Controllers.Channels.UsersChannelsControllers.ChannelPlaylistsController;
import com.example.youtube.Client.Controllers.Channels.UsersChannelsControllers.CreateChannelController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    @FXML
    private BorderPane border1;
    @FXML
    private BorderPane border2;
    @FXML
    private BorderPane border3; // Assuming the typo remains in the FXML file
    @FXML
    private BorderPane border4;
    @FXML
    private BorderPane border5;
    @FXML
    private BorderPane border6;
    @FXML
    private BorderPane border7;
    @FXML
    private BorderPane border8;
    @FXML
    private BorderPane border9;

    @FXML
    private TextField searchField;
    @FXML
    private List<BorderPane> borders = new ArrayList<>();


    @FXML
    private Button homeButton;

    @FXML
    private Button yourChannel;
    @FXML
    private Button SearchButton;
    @FXML
    private Button subscriptionsButton;
    private static Stage stage;
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


        borders.add(border1);
        borders.add(border2);
        borders.add(border3);
        borders.add(border4);
        borders.add(border5);
        borders.add(border6);
        borders.add(border7);
        borders.add(border8);
        borders.add(border9);

        for (BorderPane borderPane : borders) {
            ImageView imageView = (ImageView) borderPane.getCenter();
            VBox bottomVBox = (VBox) borderPane.getBottom();

            // Access and manipulate the nodes within each BorderPane
            String videoName = ((Label) bottomVBox.getChildren().get(0)).getText();
            String channelName = ((Label) bottomVBox.getChildren().get(1)).getText();
            String viewCount = ((Label) bottomVBox.getChildren().get(2)).getText();

            // Perform operations on the accessed nodes
            System.out.println("Video Name: " + videoName);
            System.out.println("Channel Name: " + channelName);
            System.out.println("View Count: " + viewCount);
        }

    }




    public static void setGetUserInfo(JSONObject getUserInfo) {
        JSONObject UserInfo= getUserInfo;
        if (UserInfo.getString("ChannelName").equals("")){
            CreateChannelController.setUserInfo(getUserInfo);
            UiController.changingscene(stage,"createChannel.fxml");
        }else {
            ChannelPlaylistsController.setUserInfo(getUserInfo);
            UiController.changingscene(stage,"channelPlaylists-view.fxml");

        }
    }
    @FXML
    private void handleYourChannel() {
        stage = (Stage) yourChannel.getScene().getWindow();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("DataManager","gettingUserInfo");
        jsonObject.put("Parameter1", ClientToServerConnection.uiController.getUsername());
        ClientToServerConnection.uiController.SetiMessage(jsonObject.toString());


    }
    @FXML
    private void handlehomeButton() {
        stage = (Stage) homeButton.getScene().getWindow();
        UiController.changingscene(stage,"login-view.fxml");
    }

    @FXML
    private void handleSearch() {
        stage = (Stage) SearchButton.getScene().getWindow();
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
