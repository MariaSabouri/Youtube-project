package com.example.youtube.Client.Controllers;

import com.example.youtube.Client.ClientToServerConnection;
import com.example.youtube.Client.Controllers.SearchbarController.SearchbarController;
import com.example.youtube.Client.Controllers.VideoViewControllers.VideoController;
import com.example.youtube.Client.UiController;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;


public class CommonTools {
    private static Stage Currentstage;
    public static void setCurrentstage(Stage currentstage) {
        Currentstage = currentstage;
    }

    public static void searchbarToll(String videoName,Stage stage){
        Currentstage=stage;
        String jsonString = "{\"DataManager\":\"SearchingVideo\",\"Parameter1\":\"" + videoName + "\"}";
        JSONObject jsonObject=new JSONObject(jsonString);
        jsonObject.put("Class","database");
        ClientToServerConnection.uiController.SetiMessage(jsonObject.toString());

    }
    public static void goSeachview(JSONArray jsonArray){
        UiController.changingscene(Currentstage,"SearchbarResullt-view.fxml");
        SearchbarController.setResuluJsonArray(jsonArray);
    }
    public static void showingError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Empty Textfield Error");
        alert.setContentText("Please fill in the textfield before pressing the button.");
        alert.showAndWait();
    }

    public static void goVideoView(JSONObject response) {
        VideoController.setGetVPCID(response);

        String videoName=response.getString("VideoName");
        String playlistName=response.getString("PlaylistName");
        String publisher=response.getString("Username");

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("Class","videoHandeling");
        jsonObject.put("videoHandelingFuctions","gettingVideoViaViedoNamePlaylistNamePublisher");
        jsonObject.put("Parameter1",videoName);
        jsonObject.put("Parameter2",playlistName);
        jsonObject.put("Parameter3",publisher);
        ClientToServerConnection.uiController.SetiMessage(jsonObject.toString());


    }
    public static void getVideo(String video){

        VideoController.ReadfileContentBase64(video);
        UiController.changingscene(Currentstage,"video-view.fxml");
    }
    public static void GetUserInfo(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("Class","database");
        jsonObject.put("DataManager","gettingUserInfo");
        jsonObject.put("Parameter1", ClientToServerConnection.uiController.getUsername());
        ClientToServerConnection.uiController.SetiMessage(jsonObject.toString());

    }


    private static JSONObject UserInfoJson;
    public static void setUsername(JSONObject jsonObject){
        ClientToServerConnection.userInfo.setInfo(jsonObject);
    }





}
