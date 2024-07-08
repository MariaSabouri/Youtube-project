package com.example.youtube.Client.Controllers.Channels.UsersChannelsControllers;

import com.example.youtube.Client.ClientToServerConnection;
import com.example.youtube.Client.Controllers.ChannelInterface;
import com.example.youtube.Client.Controllers.VideoViewControllers.VideoController;
import com.example.youtube.Client.Controllers.CommonTools;
import com.example.youtube.Client.UiController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

public class ChannelController implements Initializable,ChannelInterface {

    @FXML
    private TextField searchField;

    @FXML
    private Button homeButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button uploadVideo;

    @FXML
    private Button subscriptionButton;

    @FXML
    private Label channelNameLabel;

    @FXML
    private Label subscribersLabel;

    @FXML
    private VBox SearchResultVbox;


    private static Stage stage;
    private static JSONObject UserInfo;
    public static void setUserInfo(JSONObject userInfo) {
        UserInfo = userInfo;
    }
    private static String playlistName;
    public static void setPlaylistChoosen(String id) {
        playlistName=id;
    }
    private static JSONArray Array_videosForThisPlaylist;
    public static void VideosForThisPlaylist(JSONArray jsonArray){
        Array_videosForThisPlaylist=jsonArray;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchButton.setOnAction(event -> searchButtonhandler());
        homeButton.setOnAction(event -> homeButtonhandler());
        uploadVideo.setOnAction(event -> uploadVideoHandler());

        channelNameLabel.setText(UserInfo.getString("ChannelName"));
        subscribersLabel.setText("Subscribers: ");
        SearchResultVbox.setSpacing(10);

        final int IV_SIZE=105;


        for (int i=0;i<Array_videosForThisPlaylist.length();i++){
            JSONObject jsonObject=Array_videosForThisPlaylist.getJSONObject(i);
            BorderPane newBorderPane = new BorderPane();

            newBorderPane.setId(String.valueOf(jsonObject.getInt("VPCID")));

            ImageView newImageView = new ImageView(ChannelController.class.getResource("/com/example/youtube/videoTools/VideoImage.png").toString());
            newImageView.setFitWidth(IV_SIZE);
            newImageView.setFitHeight(IV_SIZE);
            newBorderPane.setLeft(newImageView);

            Label videoName=new Label(jsonObject.getString("VideoName"));
            videoName.setFont(new Font(14));
            videoName.setPadding(new Insets(20,0,0,40));
            Label numberOfView=new Label(String.valueOf(jsonObject.getInt("NumberOfView")));
            numberOfView.setFont(new Font(14));
            numberOfView.setPadding(new Insets(20,0,0,40));

            VBox centerVBox = new VBox();
            centerVBox.getChildren().add(videoName);
            centerVBox.getChildren().add(numberOfView);

            newBorderPane.setCenter(centerVBox);

            SearchResultVbox.getChildren().add(newBorderPane);

            newBorderPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    stage = (Stage) newBorderPane.getScene().getWindow();

                    String jsonString = "{\"DataManager\":\"VPCIDInfo\",\"Parameter1\":\"" + jsonObject.getInt("VPCID") + "\",\"Parameter2\":\""+UiController.getUsername()+"\"}";
                    JSONObject jsonObject=new JSONObject(jsonString);
                    jsonObject.put("Class","database");
                    CommonTools.setCurrentstage(stage);
                    ClientToServerConnection.uiController.SetiMessage(jsonObject.toString());
                }
            });


        }

    }

    private void uploadVideoHandler() {
        stage = (Stage) uploadVideo.getScene().getWindow();
        final File[] fileToSend=new File[1];
        uploadVideo.setOnAction(e->{

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP4 Files", "*.mp4"));

            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                fileToSend[0] = selectedFile;
                System.out.println("File selected: " + fileToSend[0].getAbsolutePath());
                SendingFileToServer(fileToSend[0]);
            }
        });


    }

    private void SendingFileToServer(File file) {
        try {
            FileInputStream fileInputStream=new FileInputStream(file.getAbsoluteFile());
            Socket socket=new Socket("localhost",UiController.getPort());

            DataOutputStream dataOutputStream=new DataOutputStream(socket. getOutputStream());

            String fileName=file.getName();

            byte[] fileContentBytes = new byte[(int) file.length()];
            fileInputStream.read(fileContentBytes);
            String fileContentBase64 = Base64.getEncoder().encodeToString(fileContentBytes);

            JSONObject jsonObject=new JSONObject();
            jsonObject.put("Class","videoHandeling");
            jsonObject.put("videoHandelingFuctions","UploadVideo");
            jsonObject.put("Parameter1",UserInfo.getString("Username"));
            jsonObject.put("Parameter2",playlistName);
            jsonObject.put("Parameter3",fileName);
            jsonObject.put("Parameter4",fileContentBase64);
            ClientToServerConnection.uiController.SetiMessage(jsonObject.toString());


        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void homeButtonhandler() {
        stage = (Stage) homeButton.getScene().getWindow();
        UiController.changingscene(stage,"homePage-view.fxml");
    }

    @Override
    public void searchButtonhandler() {
        stage = (Stage) searchButton.getScene().getWindow();
        try {
            String searchText = searchField.getText();
            if (searchText.isEmpty()){
                throw new IllegalArgumentException("Search Textfield is empty");
            }
            CommonTools.searchbarToll(searchText,stage);
            searchField.clear();
        }catch (IllegalArgumentException e){
            CommonTools.showingError();

        }
    }


}
