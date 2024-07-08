package com.example.youtube.Client.Controllers.SearchbarController;

import com.example.youtube.Client.ClientToServerConnection;
import com.example.youtube.Client.Controllers.CommonTools;
import com.example.youtube.Client.Controllers.SignUpLoginHomeControllers.HomePageController;
import com.example.youtube.Client.Controllers.VideoViewControllers.VideoController;
import com.example.youtube.Client.UiController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private static Stage stage;

    public static void setResuluJsonArray(JSONArray jsonArray) {
        JsonArray=jsonArray;
    }
    private static JSONArray getResuluJsonArray(){return JsonArray;}



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SearchResultVbox.setSpacing(10);
        JSONArray jsonArray=getResuluJsonArray();
        int i = 1;
        for (Object child : SearchResultVbox.getChildren()) {
            final int IV_SIZE=105;
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

                    Image image=new Image(HomePageController.class.getResource("/com/example/youtube/videoTools/VideoImage.png").toString());
                    ImageView imageView = (ImageView) borderPane.getLeft();
                    imageView.setFitWidth(IV_SIZE);
                    imageView.setFitHeight(IV_SIZE);
                    imageView.setImage(image);

                    videoNameLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            stage=(Stage) videoNameLabel.getScene().getWindow();

                            String jsonString = "{\"DataManager\":\"VPCIDInfo\",\"Parameter1\":\"" + jsonObject.getInt("VPCID") + "\",\"Parameter2\":\""+UiController.getUsername()+"\"}";
                            JSONObject jsonObject1=new JSONObject(jsonString);
                            jsonObject1.put("Class","database");
                            CommonTools.setCurrentstage(stage);
                            ClientToServerConnection.uiController.SetiMessage(jsonObject1.toString());
                        }
                    });
                }

                i++;
            }
        }



    }
}
