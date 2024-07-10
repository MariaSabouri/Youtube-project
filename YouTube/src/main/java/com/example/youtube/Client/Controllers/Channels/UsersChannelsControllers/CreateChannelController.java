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




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        UserInfo=ClientToServerConnection.userInfo.getInfo();

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

        String jsonString = "{\"DataManager\":\"CreatingChannel\",\"Parameter1\":\"" + ClientToServerConnection.userInfo.getInfo().getString("Username") + "\",\"Parameter2\":\"" + channelNameDecided + "\"}";
        JSONObject jsonObject=new JSONObject(jsonString);
        jsonObject.put("Class","database");
        ClientToServerConnection.uiController.SetiMessage(jsonObject.toString());

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
            ClientToServerConnection.userInfo.setInfo(UserInfo);


            JSONObject jsonObject=new JSONObject();
            jsonObject.put("Class","videoHandeling");
            jsonObject.put("videoHandelingFuctions","createUserFolder");
            jsonObject.put("Parameter1",ClientToServerConnection.userInfo.getInfo().getString("Username"));

            ClientToServerConnection.uiController.SetiMessage(jsonObject.toString());

            UiController.changingscene(stage,"homePage-view.fxml");

        }

    }

    private void homeButtonhandler() {
        stage=(Stage) homeButton.getScene().getWindow();
        UiController.changingscene(stage,"homePage-view.fxml");
    }
}
