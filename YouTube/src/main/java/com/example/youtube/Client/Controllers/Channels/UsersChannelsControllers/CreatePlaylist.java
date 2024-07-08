package com.example.youtube.Client.Controllers.Channels.UsersChannelsControllers;

import com.example.youtube.Client.ClientToServerConnection;
import com.example.youtube.Client.Controllers.CommonTools;
import com.example.youtube.Client.Controllers.SignUpLoginHomeControllers.HomePageController;
import com.example.youtube.Client.UiController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class CreatePlaylist implements Initializable {

    @FXML
    private TextField PlaylistNameField;

    @FXML
    private Button homeButton;

    @FXML
    private Button createButton;
    private static Stage stage;
    private static JSONObject UserInfo;
    public static void setUserInfo(JSONObject userInfo) {
        UserInfo = userInfo;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeButton.setOnAction(event -> homeButtonhandler());
        createButton.setOnAction(event -> CreateButtonhandler());
    }
    private static String PLaylistNameDecided;
    private void CreateButtonhandler() {
        stage=(Stage) createButton.getScene().getWindow();
        try {
            PLaylistNameDecided = PlaylistNameField.getText();
            if (PLaylistNameDecided.isEmpty()){
                throw new IllegalArgumentException("Textfield is empty");
            }
            String jsonString = "{\"DataManager\":\"CreatingPlaylist\",\"Parameter1\":\"" + ClientToServerConnection.uiController.getUsername() + "\",\"Parameter2\":\"" + PLaylistNameDecided + "\"}";
            JSONObject jsonObject=new JSONObject(jsonString);
            jsonObject.put("Class","database");
            ClientToServerConnection.uiController.SetiMessage(jsonObject.toString());

            PlaylistNameField.clear();
        }catch (IllegalArgumentException e){
            CommonTools.showingError();
        }
    }

    public static void SettingNameForPlaylist(boolean response) {
        if (!response){
            Platform.runLater(()->{
                CommonTools.showingError();
            });
        }else {
            UserInfo.getJSONArray("Playlists").put(PLaylistNameDecided);
            System.out.println(UserInfo);
            HomePageController.setGetUserInfo(UserInfo);


            JSONObject jsonObject=new JSONObject();
            jsonObject.put("Class","videoHandeling");
            jsonObject.put("videoHandelingFuctions","createUserPlaylistFolder");
            jsonObject.put("Parameter1",UserInfo.getString("Username"));
            jsonObject.put("Parameter2",PLaylistNameDecided);
            ClientToServerConnection.uiController.SetiMessage(jsonObject.toString());


            UiController.changingscene(stage,"channelPlaylists-view.fxml");

        }

    }

    private void homeButtonhandler() {
        stage=(Stage) homeButton.getScene().getWindow();
        UiController.changingscene(stage,"homePage-view.fxml");
    }
}
