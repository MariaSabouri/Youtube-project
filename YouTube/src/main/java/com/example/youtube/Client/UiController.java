package com.example.youtube.Client;

import com.example.youtube.Client.Controllers.Channels.UsersChannelsControllers.ChannelController;
import com.example.youtube.Client.Controllers.CommonTools;
import com.example.youtube.Client.Controllers.SearchbarController.SearchbarController;
import com.example.youtube.Client.Controllers.SignUpLoginHomeControllers.HomePageController;
import com.example.youtube.Client.Controllers.SignUpLoginHomeControllers.LoginController;
import com.example.youtube.Client.Controllers.SignUpLoginHomeControllers.SignUpController;
import com.example.youtube.Client.Controllers.Channels.UsersChannelsControllers.CreateChannelController;
import com.example.youtube.Client.Controllers.Channels.UsersChannelsControllers.CreatePlaylist;
import com.example.youtube.FXML_Loader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONObject;


import java.io.*;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class UiController {
    public static String Message;
    private static int Port=6669;

    public static int getPort() {
        return Port;
    }

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public static String getUsername() {
        return Username;
    }

    private static String Username;
    public static void SetiMessage(String m){Message=m;}

    public UiController() {
        try {
            try {
                // Connecting Client to the server
                this.socket= new Socket("localhost", Port);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            recieveMessage();
            sendingrequest();

        }catch (IOException e){
            CloseEveryThing(socket,bufferedReader,bufferedWriter);
        }
    }

    public static void setUsername(String username) {
        Username=username;
    }


    public void sendingrequest() {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (socket.isConnected()){
                            while (Message!=null){
                                bufferedWriter.write(Message);
                                bufferedWriter.newLine();
                                bufferedWriter.flush();
                                System.out.println("Server: sended");
                                Message=null;

                            }
                        }
                    }catch (IOException e){
                        CloseEveryThing(socket,bufferedReader,bufferedWriter);
                    }
                }
            }).start();
    }
    public void recieveMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageToRead;
                while (socket.isConnected()){
                    try {
                        messageToRead=bufferedReader.readLine();
                        OrientingToAClassBuyServerResponse(messageToRead);
                        //System.out.println(messageToRead);
                    }catch (IOException e){
                        CloseEveryThing(socket,bufferedReader,bufferedWriter);
                    }
                }
            }
        }).start();

    }


    private void OrientingToAClassBuyServerResponse(String messageToRead) {
        try {
            JSONObject jsonObject = new JSONObject(messageToRead);

            String UiClass=jsonObject.getString("Class");

            if (UiClass.equals("LoginController")){
                if (jsonObject.getBoolean("Response")==true){
                    LoginController.goHomeView(true);
                }else {LoginController.goHomeView(false);}

            } else if (UiClass.equals("SignUpController")) {
                if (jsonObject.getBoolean("Response")==true){
                    SignUpController.goHomeView(true);
                }else{SignUpController.goHomeView(false);}

            } else if (UiClass.equals("commonToolSearchBar")) {
                CommonTools.goSeachview(jsonObject.getJSONArray("Response"));

            }else if (UiClass.equals("CommonTools/goVideoView")) {
                CommonTools.goVideoView(jsonObject.getJSONObject("Response"));

            }else if (UiClass.equals("CommonTools/setUserInfo")){
                CommonTools.setUserInfo(jsonObject.getJSONObject("Response"));

            }else if (UiClass.equals("CreateChannelController/SettingNameForChannel")) {
                CreateChannelController.SettingNameForChannel(jsonObject.getBoolean("Response"));

            } else if (UiClass.equals("ChannelPlaylistsController/SettingNameForPlaylist")) {
                CreatePlaylist.SettingNameForPlaylist(jsonObject.getBoolean("Response"));

            } else if (UiClass.equals("HomePageController/setHomepageTrendVideos")) {
                //HomePageController.setHomepageTrendVideos(jsonObject.getJSONArray("Response"));

            }else if (UiClass.equals("ChannelController/VideosForThisPlaylist")) {
                ChannelController.VideosForThisPlaylist(jsonObject.getJSONArray("Response"));

            } else if (UiClass.equals("CommonTools/getVideo")) {
                CommonTools.getVideo(jsonObject.getString("Response"));

            } else if (UiClass.equals("CommonTools/setLikeAndDislikeStatistics")) {
                CommonTools.setLikeAndDislikeStatistics(jsonObject.getJSONObject("Response"));

            } else if (UiClass.equals("CommonTools/updateViewsNubmerForVideo")) {
                CommonTools.updateViewsNubmerForVideo();

            }

        }catch (Exception e){
            System.out.println("May got null answer!");
        }


    }


    @FXML
    public static void changingscene(Stage stage,String fxml){
        Platform.runLater(() -> {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(FXML_Loader.loadURL(fxml));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            stage.setScene(scene);

            stage.show();
        }catch (IOException e){
            System.out.println("There is an error in changing scene");
        e.printStackTrace();}
        });
    }





    private static void CloseEveryThing(Socket s,BufferedReader bufferedReader,BufferedWriter bufferedWriter){
        try {
            if (s!=null){
                s.close();
            }
            if (bufferedReader!=null){
                bufferedReader.close();
            }
            if (bufferedWriter!=null){
                bufferedWriter.close();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }





}
