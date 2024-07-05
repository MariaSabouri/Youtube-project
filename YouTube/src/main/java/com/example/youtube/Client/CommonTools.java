package com.example.youtube.Client;

import javafx.stage.Stage;
import org.json.JSONArray;


public class CommonTools {
    private static Stage Currentstage;
    public static void searchbarToll(String videoName,Stage stage){
        Currentstage=stage;
        String jsonString = "{\"DataManager\":\"SearchingVideo\",\"Parameter1\":\"" + videoName + "\"}";
        ClientToServerConnection.uiController.SetiMessage(jsonString);

    }
    public static void goSeachview(JSONArray jsonArray){
        UiController.changingscene(Currentstage,"SearchbarResullt-view.fxml");
        SearchbarController.setResuluJsonArray(jsonArray);
    }


}
