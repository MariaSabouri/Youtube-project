package com.example.youtube.Client.Controllers;

import com.example.youtube.Client.ClientToServerConnection;
import com.example.youtube.Client.Controllers.SearchbarController.SearchbarController;
import com.example.youtube.Client.Controllers.VideoViewControllers.VideoController;
import com.example.youtube.Client.UiController;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;


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


/////
    private static JSONObject VPCID;
    public static void goVideoView(JSONObject response) {
        VPCID=response;
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

        JSONObject jsonObject1=new JSONObject();
        jsonObject1.put("Class","database");
        jsonObject1.put("DataManager","ViewCounterForVPCID");
        jsonObject1.put("Parameter1",String.valueOf(VPCID.getInt("VPCID")));
        jsonObject1.put("Parameter2",ClientToServerConnection.userInfo.getInfo().getString("Username"));
        ClientToServerConnection.uiController.SetiMessage(jsonObject1.toString());

    }

    public static void updateViewsNubmerForVideo(){
        getLikeAndDislikeStatistics();
    }

    public static void getLikeAndDislikeStatistics() {
        JSONObject jsonObject2=new JSONObject();
        jsonObject2.put("Class","database");
        jsonObject2.put("DataManager","getUserLikeAndDislikeAction");
        jsonObject2.put("Parameter1",String.valueOf(VPCID.getInt("VPCID")));
        jsonObject2.put("Parameter2",ClientToServerConnection.userInfo.getInfo().getString("Username"));
        ClientToServerConnection.uiController.SetiMessage(jsonObject2.toString());
    }

    public static void setLikeAndDislikeStatistics(JSONObject jsonObject){
        VideoController.setLikeAndDislikeStatistics(jsonObject);
        ClientToServerConnection.uiController.changingscene(Currentstage,"video-view.fxml");

    }
/////





    public static void setUserInfo(JSONObject getUserInfo) {
        try {
            ClientToServerConnection.userInfo.setInfo(getUserInfo);
            UiController.changingscene(Currentstage,"homePage-view.fxml");

        }catch (Exception e){
            System.out.println("There is an error!");
        }
    }


    public static void UpdateLikeAndDislikeActionsOnDataBase(JSONObject likeAndDislike) {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("Class","database");
        jsonObject.put("DataManager","UpdateLikeAndDislikeActionsOnDataBase");
        jsonObject.put("Parameter1",likeAndDislike);
        ClientToServerConnection.uiController.SetiMessage(jsonObject.toString());

    }
}
