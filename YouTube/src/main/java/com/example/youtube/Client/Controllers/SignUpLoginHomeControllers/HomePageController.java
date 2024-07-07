package com.example.youtube.Client.Controllers.SignUpLoginHomeControllers;

import com.example.youtube.Client.ClientToServerConnection;
import com.example.youtube.Client.Controllers.ChannelInterface;
import com.example.youtube.Client.Controllers.Channels.VideoViewControllers.VideoController;
import com.example.youtube.Client.Controllers.CommonTools;
import com.example.youtube.Client.UiController;
import com.example.youtube.Client.Controllers.Channels.UsersChannelsControllers.ChannelPlaylistsController;
import com.example.youtube.Client.Controllers.Channels.UsersChannelsControllers.CreateChannelController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomePageController implements Initializable, ChannelInterface {
    @FXML
    private BorderPane border1;
    @FXML
    private BorderPane border2;
    @FXML
    private BorderPane border3;
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
    private static List<BorderPane> borders;

    @FXML
    private Button homeButton;

    @FXML
    private Button yourChannel;
    @FXML
    private Button SearchButton;
    @FXML
    private Button subscriptionsButton;
    private static Stage stage;

    private static JSONObject UserInfo;
    public static void setUserInfo(JSONObject userInfo) {
        UserInfo = userInfo;
    }

    private static JSONArray HomepageTrendVideos;

    public static void setHomepageTrendVideos(JSONArray homepageTrendVideos) {
        HomepageTrendVideos = homepageTrendVideos;
        setViewForTrendVideos();

    }
    @FXML
    private static void setViewForTrendVideos() {
        final int IV_SIZE=105;
        int i=0;
        for (BorderPane borderPane : borders) {

            JSONObject jsonObject1= (JSONObject) HomepageTrendVideos.get(i);
            borderPane.setId(String.valueOf(jsonObject1.getInt("VPCID")));

            Image image=new Image(HomePageController.class.getResource("/com/example/youtube/videoTools/VideoImage.png").toString());
            ImageView imageView = (ImageView) borderPane.getCenter();
            imageView.setFitWidth(IV_SIZE);
            imageView.setFitHeight(IV_SIZE);
            imageView.setImage(image);

            VBox bottomVBox = (VBox) borderPane.getBottom();
            Platform.runLater(()->{
                ((Label) bottomVBox.getChildren().get(0)).setText(jsonObject1.getString("VideoName"));
                ((Label) bottomVBox.getChildren().get(1)).setText(jsonObject1.getString("ChannelName"));
                ((Label) bottomVBox.getChildren().get(2)).setText(String.valueOf(jsonObject1.getInt("NumberOfView")));
            });

            borderPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    stage = (Stage) borderPane.getScene().getWindow();
                    VideoController.setUserInfo(UserInfo);
                    VideoController.setGetVPCID(borderPane.getId());
                    UiController.changingscene(stage,"video-view.fxml");
                }
            });

            i++;
            if (i==HomepageTrendVideos.length()){
                break;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        borders = new ArrayList<>();
        borders.add(border1);
        borders.add(border2);
        borders.add(border3);
        borders.add(border4);
        borders.add(border5);
        borders.add(border6);
        borders.add(border7);
        borders.add(border8);
        borders.add(border9);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("Class","database");
        jsonObject.put("DataManager","TrendVPCIDForHomePage");
        ClientToServerConnection.uiController.SetiMessage(jsonObject.toString());


        homeButton.setOnAction(event -> homeButtonhandler());
        yourChannel.setOnAction(event -> handleYourChannel());
        SearchButton.setOnAction(event -> searchButtonhandler());




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
        jsonObject.put("Class","database");
        jsonObject.put("DataManager","gettingUserInfo");
        jsonObject.put("Parameter1", ClientToServerConnection.uiController.getUsername());
        ClientToServerConnection.uiController.SetiMessage(jsonObject.toString());


    }



    @Override
    public void homeButtonhandler() {
        stage = (Stage) homeButton.getScene().getWindow();
        UiController.changingscene(stage,"login-view.fxml");
    }

    @Override
    public void searchButtonhandler() {
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
