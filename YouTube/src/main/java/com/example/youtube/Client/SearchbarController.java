package com.example.youtube.Client;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchbarController implements Initializable {
    static JSONArray JsonArray;
    @FXML
    private Button ChannelButton;

    @FXML
    private Button HomeButton;

    @FXML
    private VBox SearchResultVbox;

    @FXML
    private TextField searchField;
    private static Node source;

    public static void setResuluJsonArray(JSONArray jsonArray) {
        JsonArray=jsonArray;
    }
    private static JSONArray getResuluJsonArray(){return JsonArray;}

    public static void goVideoView(JSONObject response) {
        Stage stage = (Stage) source.getScene().getWindow();
        UiController.changingscene(stage,"video-view.fxml");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JSONArray jsonArray=getResuluJsonArray();
        int i = 1;
        for (Object child : SearchResultVbox.getChildren()) {
            if (child instanceof BorderPane) {
                JSONObject jsonObject;
                BorderPane borderPane = (BorderPane) child;

                if (i>jsonArray.length()){
                    borderPane.setVisible(false);
                }else {
                    Label videoNameLabel = (Label) borderPane.getCenter().lookup("#video"+String.valueOf(i));
                    Label channelNameLabel = (Label) borderPane.getCenter().lookup("#channel"+String.valueOf(i));
                    Label viewCountLabel = (Label) borderPane.getCenter().lookup("#view"+String.valueOf(i));
                    jsonObject=(JSONObject) jsonArray.get(i-1);
                    videoNameLabel.setText(jsonObject.getString("VideoName"));
                    channelNameLabel.setText(jsonObject.getString("ChannelName"));
                    viewCountLabel.setText(String.valueOf(jsonObject.getInt("NumberOfView")));
                    videoNameLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            source = (Node) event.getSource();
                            String jsonString = "{\"DataManager\":\"VPCIDInfo\",\"Parameter1\":\"" + jsonObject.getInt("VPCID") + "\",\"Parameter2\":\""+UiController.getUsername()+"\"}";
                            ClientToServerConnection.uiController.SetiMessage(jsonString);
                        }
                    });
                }

                i++;
            }
        }



    }
}
