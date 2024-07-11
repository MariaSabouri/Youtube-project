package com.example.youtube.client.controllers.channels.UsersChannelsControllers;

import com.example.youtube.client.ClientToServerConnection;
import com.example.youtube.client.controllers.CommonTools;
import com.example.youtube.client.UiController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONArray;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserInfo=ClientToServerConnection.userInfo.getInfo();

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
            try {
                UserInfo.getJSONArray("Playlists").put(PLaylistNameDecided);
            }catch (Exception e){
                JSONArray jsonArray=new JSONArray();
                jsonArray.put(PLaylistNameDecided);
                UserInfo.put("Playlists",jsonArray);
                System.out.println("");
            }finally {

                ClientToServerConnection.userInfo.setInfo(UserInfo);


                JSONObject jsonObject=new JSONObject();
                jsonObject.put("Class","videoHandeling");
                jsonObject.put("videoHandelingFuctions","createUserPlaylistFolder");
                jsonObject.put("Parameter1",UserInfo.getString("Username"));
                jsonObject.put("Parameter2",PLaylistNameDecided);
                ClientToServerConnection.uiController.SetiMessage(jsonObject.toString());


                UiController.changingscene(stage,"channelPlaylists-view.fxml");
            }


        }

    }

    private void homeButtonhandler() {
        stage=(Stage) homeButton.getScene().getWindow();
        UiController.changingscene(stage,"homePage-view.fxml");
    }
}
