package com.example.youtube.Client.Controllers.Channels.UsersChannelsControllers;

import com.example.youtube.Client.ClientToServerConnection;
import com.example.youtube.Client.Controllers.ChannelInterface;
import com.example.youtube.Client.Controllers.CommonTools;
import com.example.youtube.Client.UiController;
import javafx.event.ActionEvent;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.scene.control.ScrollPane;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ChannelPlaylistsController implements Initializable, ChannelInterface {
    @FXML
    private Label channelNameLabel;
    @FXML
    private VBox SearchResultVbox;

    @FXML
    private Button homeButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;
    @FXML
    private BorderPane borderPane;

    @FXML
    private ScrollPane ScrollPane;

    @FXML
    private Label subscribersLabel;

    @FXML
    private Button subscriptionButton;

    @FXML
    private Button creatPlaylist;
    private static JSONObject UserInfo;
    private static Stage stage;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserInfo=ClientToServerConnection.userInfo.getInfo();

        homeButton.setOnAction(event -> homeButtonhandler());
        searchButton.setOnAction(event -> searchButtonhandler());
        creatPlaylist.setOnAction(event -> creatPlaylisthandler());

        try {
            JSONArray jsonArray=UserInfo.getJSONArray("Playlists");
            channelNameLabel.setText(UserInfo.getString("ChannelName"));

            final int IV_SIZE=105;

            SearchResultVbox.setSpacing(10);
            for (int i=0;i<jsonArray.length();i++){
                BorderPane newBorderPane = new BorderPane();
                newBorderPane.setId(jsonArray.getString(i));

                ImageView newImageView = new ImageView(getClass().getResource("/com/example/youtube/videoTools/playlist_image.jpg").toString());
                newImageView.setFitWidth(IV_SIZE);
                newImageView.setFitHeight(IV_SIZE);
                newBorderPane.setLeft(newImageView);

                Label newLabel = new Label(jsonArray.getString(i));

                VBox centerVBox = new VBox();
                newLabel.setFont(new Font(14));
                newLabel.setPadding(new Insets(20,0,0,40));
                centerVBox.getChildren().add(newLabel);
                newBorderPane.setCenter(centerVBox);

                SearchResultVbox.getChildren().add(newBorderPane);
                newBorderPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        stage = (Stage) newBorderPane.getScene().getWindow();

                        ChannelController.setPlaylistChoosen(newBorderPane.getId());

                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put("Class","database");
                        jsonObject.put("DataManager","gettingAllUserVPCID");
                        jsonObject.put("Parameter1",UserInfo.getString("Username"));
                        jsonObject.put("Parameter2",newBorderPane.getId());
                        ClientToServerConnection.uiController.SetiMessage(jsonObject.toString());

                        UiController.changingscene(stage,"channel-view.fxml");
                    }
                });
            }
        }catch (Exception e){
            System.out.println("User don't have any playlist!");

        }

    }



    private void creatPlaylisthandler() {
        stage=(Stage) creatPlaylist.getScene().getWindow();

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
