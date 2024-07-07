package com.example.youtube.Client.Controllers;

import com.example.youtube.Client.ClientToServerConnection;
import com.example.youtube.Client.Controllers.SearchbarController.SearchbarController;
import com.example.youtube.Client.UiController;
import javafx.scene.control.Alert;
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
    public static void showingError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Empty Textfield Error");
        alert.setContentText("Please fill in the textfield before pressing the button.");
        alert.showAndWait();
    }


}
