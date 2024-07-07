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

public class CreateChannelController implements Initializable {

    @FXML
    private TextField ChannelNameField;

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
        createButton.setOnAction(event -> createButtonhandler());
    }
    private static String channelNameDecided;
    private void createButtonhandler() {
        stage=(Stage) createButton.getScene().getWindow();
        try {
            channelNameDecided = ChannelNameField.getText();
        if (channelNameDecided.isEmpty()){
            throw new IllegalArgumentException("Textfield is empty");
        }
        UserInfo.put("ChannelName",channelNameDecided);
        String jsonString = "{\"DataManager\":\"CreatingChannel\",\"Parameter1\":\"" + ClientToServerConnection.uiController.getUsername() + "\",\"Parameter2\":\"" + channelNameDecided + "\"}";
        ClientToServerConnection.uiController.SetiMessage(jsonString);
        ChannelNameField.clear();
        }catch (IllegalArgumentException e){
            CommonTools.showingError();
        }


    }
    public static void SettingNameForChannel(boolean response) {
        if (!response){
            Platform.runLater(()->{
                CommonTools.showingError();
            });
        }else {
            UserInfo.put("ChannelName",channelNameDecided);
            HomePageController.setGetUserInfo(UserInfo);
            UiController.changingscene(stage,"homePage-view.fxml");

        }

    }

    private void homeButtonhandler() {
        stage=(Stage) homeButton.getScene().getWindow();
        UiController.changingscene(stage,"homePage-view.fxml");
    }
}
