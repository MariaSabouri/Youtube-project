package com.example.youtube.Client.Controllers.VideoViewControllers;

import com.example.youtube.Client.ClientToServerConnection;
import com.example.youtube.Client.Controllers.ChannelInterface;
import com.example.youtube.Client.Controllers.Channels.ChannelsConltrollers.VisitingChannelPlaylist;
import com.example.youtube.Client.Controllers.CommonTools;
import com.example.youtube.Client.UiController;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

public class VideoController implements Initializable,ChannelInterface {
    private static Stage stage;

    @FXML
    private Button postComment;

    @FXML
    private Label numOfLiksLabel;

    @FXML
    private Button home_button;

    @FXML
    private Button searchButton;

    @FXML
    private Label UsernameLeterLabel;

    @FXML
    private TextField searchField;

    @FXML
    private Label titleLabel;

    @FXML
    private Label video;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Button likeButton;

    @FXML
    private Button subscribe;

    @FXML
    private Button dislikeButton;

    @FXML
    private Button addToWatchLaterButton;

    @FXML
    private Button channelButton;

    @FXML
    private VBox commentsBox;

    @FXML
    private TextArea commentInput;
    @FXML
    private VBox vboxParent;
    @FXML
    private MediaView mvVideo;
    private MediaPlayer mpVideo;
    private Media mediaVideo;
    @FXML
    private HBox hboxControls;
    @FXML
    private HBox hboxVolume;
    @FXML
    private Button buttonPPR;

    @FXML
    private Label labelCurrentTime;
    @FXML
    private Label labelTotalTime;

    @FXML
    private Label labelfullScreen;
    @FXML
    private  Label lableSpeed;
    @FXML
    private Label labelVolume;

    @FXML
    private Slider sliderTime;

    @FXML
    private Slider sliderVolume;

    private Boolean atEndOfVideo=false;
    private Boolean isPlaying=false;
    private Boolean isMuted=true;

    private ImageView ivPlay;
    private ImageView ivPause;
    private ImageView ivRestart;
    private ImageView ivVolume;
    private ImageView ivFullScreen;
    private ImageView ivMute;
    private ImageView ivExit;

    @FXML
    private Label DisLikeLabel;

    @FXML
    private Label LikeLabel;
    @FXML
    private Label views;


    private static JSONObject UserInfo;

    private static JSONObject likeAndDislike;
    public static void setLikeAndDislikeStatistics(JSONObject likeAndDislikeStatistics) {
        likeAndDislike=likeAndDislikeStatistics;
    }

    private static JSONObject GetVPCIfo;
    public static void setGetVPCID(JSONObject getVPCID) {
        GetVPCIfo = getVPCID;
    }


    private static File tempFile;
    public static void ReadfileContentBase64(String video){
        byte[] fileContentBytes = Base64.getDecoder().decode(video);
        try {
            tempFile = File.createTempFile("tempvideo", ".mp4");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(fileContentBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserInfo= ClientToServerConnection.userInfo.getInfo();

        titleLabel.setText(GetVPCIfo.getString("VideoName"));

        LikeLabel.setText(String.valueOf(likeAndDislike.getInt("NumberOfLike")));
        DisLikeLabel.setText(String.valueOf(likeAndDislike.getInt("NumberOfDislike")));
        setInitialColorForLikeAndDislike();

        channelButton.setText(GetVPCIfo.getString("ChannelName"));
        views.setText(String.valueOf(1+GetVPCIfo.getInt("NumberOfView")));
        descriptionLabel.setText("");

        home_button.setOnAction(event -> homeButtonhandler());
        searchButton.setOnAction(event -> searchButtonhandler());
        likeButton.setOnAction(event -> handleLike());
        dislikeButton.setOnAction(event -> handleDislike());
        channelButton.setOnAction(event -> handleShowChannel());
        subscribe.setOnAction(event -> handleSubscribe());





        final int IV_SIZE=25;

        mediaVideo=new Media(tempFile.toURI().toString());
        mpVideo=new MediaPlayer(mediaVideo);
        mvVideo.setMediaPlayer(mpVideo);

        Image imagePlay=new Image(getClass().getResource("/com/example/youtube/videoTools/play_button.png").toString());
        ivPlay=new ImageView(imagePlay);
        ivPlay.setFitWidth(IV_SIZE);
        ivPlay.setFitHeight(IV_SIZE);

        Image imageStop=new Image(getClass().getResource("/com/example/youtube/videoTools/Stop_button.png").toString());
        ivPause=new ImageView(imageStop);
        ivPause.setFitWidth(IV_SIZE);
        ivPause.setFitHeight(IV_SIZE);

        Image imageRestart=new Image(getClass().getResource("/com/example/youtube/videoTools/Restart_button.png").toString());
        ivRestart=new ImageView(imageRestart);
        ivRestart.setFitWidth(IV_SIZE);
        ivRestart.setFitHeight(IV_SIZE);

        Image imageVol=new Image(getClass().getResource("/com/example/youtube/videoTools/volum_image.png").toString());
        ivVolume=new ImageView(imageVol);
        ivVolume.setFitWidth(IV_SIZE);
        ivVolume.setFitHeight(IV_SIZE);

        Image imageFull=new Image(getClass().getResource("/com/example/youtube/videoTools/fullScreen_image.png").toString());
        ivFullScreen=new ImageView(imageFull);
        ivFullScreen.setFitWidth(IV_SIZE);
        ivFullScreen.setFitHeight(IV_SIZE);

        Image imageMute=new Image(getClass().getResource("/com/example/youtube/videoTools/Mute_image.png").toString());
        ivMute=new ImageView(imageMute);
        ivMute.setFitWidth(IV_SIZE);
        ivMute.setFitHeight(IV_SIZE);

        Image imageExit=new Image(getClass().getResource("/com/example/youtube/videoTools/ExitScreen_image.png").toString());
        ivExit=new ImageView(imageExit);
        ivExit.setFitWidth(IV_SIZE);
        ivExit.setFitHeight(IV_SIZE);

        buttonPPR.setGraphic(ivPlay);
        labelVolume.setGraphic(ivMute);
        lableSpeed.setText("1X");
        labelfullScreen.setGraphic(ivFullScreen);

        buttonPPR.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                bindCurrentTimeLabel();
                Button buttonPlay=(Button) event.getSource();
                if (atEndOfVideo){
                    sliderTime.setValue(0);
                    atEndOfVideo=false;
                    isPlaying=false;


                }
                if (isPlaying){
                    buttonPlay.setGraphic(ivPlay);
                    mpVideo.pause();
                    isPlaying=false;
                }else {
                    buttonPlay.setGraphic(ivPause);
                    mpVideo.play();
                    isPlaying=true;
                }
            }
        });


        hboxVolume.getChildren().remove(sliderVolume);

        mpVideo.volumeProperty().bindBidirectional(sliderVolume.valueProperty());

        bindCurrentTimeLabel();

        sliderVolume.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mpVideo.setVolume(sliderVolume.getValue());
                if (mpVideo.getVolume() !=0.0){
                    labelVolume.setGraphic(ivVolume);
                    isMuted=false;
                }else {
                    labelVolume.setGraphic(ivMute);
                    isMuted=true;
                }
            }
        });

        lableSpeed.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (lableSpeed.getText().equals("1X")){
                    lableSpeed.setText("2X");
                    mpVideo.setRate(2.0);
                }else {
                    lableSpeed.setText("1X");
                    mpVideo.setRate(1.0);
                }
            }
        });
        labelVolume.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (isMuted){
                    labelVolume.setGraphic(ivVolume);
                    sliderVolume.setValue(0.2);
                    isMuted=false;
                }else {
                    labelVolume.setGraphic(ivMute);
                    sliderVolume.setValue(0);
                    isMuted=true;
                }
            }
        });

        labelVolume.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (hboxVolume.lookup("#sliderVolume")==null){
                    hboxVolume.getChildren().add(sliderVolume);
                    sliderVolume.setValue(mpVideo.getVolume());
                }
            }
        });

        hboxVolume.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hboxVolume.getChildren().remove(sliderVolume);
            }
        });

        vboxParent.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observableValue, Scene oldScene, Scene newScene) {
                if (oldScene==null && newScene!=null){
                    mvVideo.fitHeightProperty().bind(newScene.heightProperty().subtract(hboxControls.heightProperty().add(20)));
                }
            }
        });
        labelfullScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Label label=(Label) mouseEvent.getSource();
                Stage stage=(Stage) label.getScene().getWindow();
                if (stage.isFullScreen()){
                    stage.setFullScreen(false);
                    labelfullScreen.setGraphic(ivFullScreen);
                }else {
                    stage.setFullScreen(true);
                    labelfullScreen.setGraphic(ivExit);
                }
                stage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        if (keyEvent.getCode()== KeyCode.ESCAPE){
                            labelfullScreen.setGraphic(ivFullScreen);
                        }
                    }
                });

            }
        });
        mpVideo.totalDurationProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration oldDuration, Duration newDuration) {
                bindCurrentTimeLabel();
                sliderTime.setMax(newDuration.toSeconds());
                labelTotalTime.setText(getTime(newDuration));
            }
        });

        sliderTime.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean wasChanging, Boolean isChanging) {
                bindCurrentTimeLabel();
                if (!isChanging){
                    mpVideo.seek(Duration.seconds(sliderTime.getValue()));
                }
            }
        });

        sliderTime.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                bindCurrentTimeLabel();
                double currentTime=mpVideo.getCurrentTime().toSeconds();
                if (Math.abs(currentTime-newValue.doubleValue())>0.5){
                    mpVideo.seek(Duration.seconds(newValue.doubleValue()));
                }
                labelMatchEndVideo(labelCurrentTime.getText(),labelTotalTime.getText());
            }
        });
        mpVideo.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration oldTime, Duration newTime) {
                bindCurrentTimeLabel();
                if (!sliderTime.isValueChanging()){
                    sliderTime.setValue(newTime.toSeconds());
                }
                labelMatchEndVideo(labelCurrentTime.getText(),labelTotalTime.getText());
            }
        });
        mpVideo.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                buttonPPR.setGraphic(ivRestart);
                atEndOfVideo=true;
                if (!labelCurrentTime.textProperty().equals(labelTotalTime.getText())){
                    labelCurrentTime.textProperty().unbind();
                    labelCurrentTime.setText(getTime(mpVideo.getTotalDuration())+" / ");
                }
            }
        });






    }

    private void bindCurrentTimeLabel() {
        labelCurrentTime.textProperty().bind(Bindings.createStringBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return getTime(mpVideo.getCurrentTime())+" / ";
            }
        },mpVideo.currentTimeProperty()));
    }

    public String getTime(Duration time){
        int hours=(int) time.toHours();
        int minutes=(int) time.toMinutes();
        int seconds=(int) time.toSeconds();

        if (seconds>59) seconds=seconds % 60;
        if (minutes>59) minutes=minutes % 60;
        if (hours > 59) hours=hours % 60;

        if (hours >0) return String.format("%d:%02d:%%02d",
                hours,
                minutes,
                seconds);
        else return String.format("%02d:%02d",
                minutes,
                seconds);



    }
    public void labelMatchEndVideo(String labelTime,String labelTotalTime){
        for (int i=0;i<labelTotalTime.length();i++){
            if (labelTime.charAt(i)!=labelTotalTime.charAt(i)){
                atEndOfVideo=false;
                if (isPlaying) buttonPPR.setGraphic(ivPause);
                else  buttonPPR.setGraphic(ivPlay);
                break;
            }else {
                atEndOfVideo=true;
                buttonPPR.setGraphic(ivRestart);
            }

        }
    }
    private void setInitialColorForLikeAndDislike() {
        if (likeAndDislike.getBoolean("Like")==true){
            LikeLabel.setTextFill(Color.GOLD);
        } else if (likeAndDislike.getBoolean("DisLike")==true) {
            DisLikeLabel.setTextFill(Color.GOLD);
        }
    }


    @FXML
    private void handleLike() {
        if (!likeAndDislike.getBoolean("Like")){
            if (likeAndDislike.getBoolean("DisLike")){
                likeAndDislike.put("DisLike",false);
                DisLikeLabel.setTextFill(Color.BLACK);
                DisLikeLabel.setText(String.valueOf(likeAndDislike.getInt("NumberOfDislike")-1));
                likeAndDislike.put("NumberOfDislike",likeAndDislike.getInt("NumberOfDislike")-1);
            }
            LikeLabel.setText(String.valueOf(likeAndDislike.getInt("NumberOfLike")+1));
            likeAndDislike.put("NumberOfLike",likeAndDislike.getInt("NumberOfLike")+1);
            LikeLabel.setTextFill(Color.GOLD);

        }else {LikeLabel.setTextFill(Color.BLACK);
            LikeLabel.setText(String.valueOf(likeAndDislike.getInt("NumberOfLike")-1));
            likeAndDislike.put("NumberOfLike",likeAndDislike.getInt("NumberOfLike")-1);}

        likeAndDislike.put("Like",!likeAndDislike.getBoolean("Like"));

        CommonTools.UpdateLikeAndDislikeActionsOnDataBase(likeAndDislike);
        System.out.println(likeAndDislike);
    }

    @FXML
    private void handleDislike() {
        if (!likeAndDislike.getBoolean("DisLike")){
            if (likeAndDislike.getBoolean("Like")){
                likeAndDislike.put("Like",false);
                LikeLabel.setTextFill(Color.BLACK);
                LikeLabel.setText(String.valueOf(likeAndDislike.getInt("NumberOfLike")-1));
                likeAndDislike.put("NumberOfLike",likeAndDislike.getInt("NumberOfLike")-1);
            }
            DisLikeLabel.setText(String.valueOf(likeAndDislike.getInt("NumberOfDislike")+1));
            likeAndDislike.put("NumberOfDislike",likeAndDislike.getInt("NumberOfDislike")+1);
            DisLikeLabel.setTextFill(Color.GOLD);

        }else {DisLikeLabel.setTextFill(Color.BLACK);
            DisLikeLabel.setText(String.valueOf(likeAndDislike.getInt("NumberOfDislike")-1));
            likeAndDislike.put("NumberOfDislike",likeAndDislike.getInt("NumberOfDislike")-1);}

        likeAndDislike.put("DisLike",!likeAndDislike.getBoolean("DisLike"));

        CommonTools.UpdateLikeAndDislikeActionsOnDataBase(likeAndDislike);
        System.out.println(likeAndDislike);
    }



    @FXML
    private void handleShowChannel() {
        stage=(Stage) channelButton.getScene().getWindow();


    }

    @FXML
    private void handleSubscribe() {System.out.println("Subscribed!");}

    @FXML
    private void handlePostComment() {
        String commentText = commentInput.getText().trim();
        if (!commentText.isEmpty()) {
            Label commentLabel = new Label(commentText);
            commentLabel.setWrapText(true);
            commentLabel.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            commentsBox.getChildren().add(commentLabel);
            commentInput.clear();
        }
    }


    @Override
    public void homeButtonhandler() {
        stage = (Stage) home_button.getScene().getWindow();
        if (mpVideo != null) {
            mpVideo.stop();
            mpVideo.dispose();
        }
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
