package com.example.youtube.Client.Controllers.Channels.UsersChannelsControllers;

import com.example.youtube.Client.Controllers.ChannelInterface;
import com.example.youtube.Client.Controllers.Channels.VideoViewControllers.VideoController;
import com.example.youtube.Client.Controllers.CommonTools;
import com.example.youtube.Client.UiController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class ChannelController implements Initializable,ChannelInterface {

    @FXML
    private TextField searchField;

    @FXML
    private Button homeButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button subscriptionButton;

    @FXML
    private Label channelNameLabel;

    @FXML
    private Label subscribersLabel;

    @FXML
    private VBox SearchResultVbox;


    private static Stage stage;
    private static JSONObject UserInfo;
    public static void setUserInfo(JSONObject userInfo) {
        UserInfo = userInfo;
    }
    private static String playlistName;
    public static void setPlaylistChoosen(String id) {
        playlistName=id;
    }
    private static JSONArray Array_videosForThisPlaylist;
    public static void VideosForThisPlaylist(JSONArray jsonArray){
        Array_videosForThisPlaylist=jsonArray;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchButton.setOnAction(event -> searchButtonhandler());
        homeButton.setOnAction(event -> homeButtonhandler());

        channelNameLabel.setText(UserInfo.getString("ChannelName"));
        subscribersLabel.setText("Subscribers: ");
        SearchResultVbox.setSpacing(10);

        final int IV_SIZE=105;


        for (int i=0;i<Array_videosForThisPlaylist.length();i++){
            JSONObject jsonObject=Array_videosForThisPlaylist.getJSONObject(i);
            BorderPane newBorderPane = new BorderPane();

            newBorderPane.setId(String.valueOf(jsonObject.getInt("VPCID")));

            ImageView newImageView = new ImageView(ChannelController.class.getResource("/com/example/youtube/videoTools/VideoImage.png").toString());
            newImageView.setFitWidth(IV_SIZE);
            newImageView.setFitHeight(IV_SIZE);
            newBorderPane.setLeft(newImageView);

            Label videoName=new Label(jsonObject.getString("VideoName"));
            videoName.setFont(new Font(14));
            videoName.setPadding(new Insets(20,0,0,40));
            Label numberOfView=new Label(String.valueOf(jsonObject.getInt("NumberOfView")));
            numberOfView.setFont(new Font(14));
            numberOfView.setPadding(new Insets(20,0,0,40));

            VBox centerVBox = new VBox();
            centerVBox.getChildren().add(videoName);
            centerVBox.getChildren().add(numberOfView);

            newBorderPane.setCenter(centerVBox);

            SearchResultVbox.getChildren().add(newBorderPane);

            newBorderPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    stage = (Stage) newBorderPane.getScene().getWindow();
                    ChannelController.setUserInfo(UserInfo);
                    VideoController.setGetVPCID(newBorderPane.getId());
                    UiController.changingscene(stage,"video-view.fxml");
                }
            });


        }

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
