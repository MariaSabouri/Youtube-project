package com.example.youtube.Client;

import com.example.youtube.FXML_Loader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientToServerConnection extends Application  {
    public static UiController uiController;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(FXML_Loader.loadURL("createChannel-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("YouTube");
//        stage. setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    public static void creatNewUiController(){
        uiController=new UiController();
    }

    public static void main(String[] args) throws IOException {
        creatNewUiController();
        launch(args);
    }
}
