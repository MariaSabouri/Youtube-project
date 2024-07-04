package com.example.youtube.Server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ServerSocket ss;

    public Server(ServerSocket ss) {
        this.ss=ss;
    }
    private void startServer(){

        try {
            while (!ss.isClosed()){
                Socket s=ss.accept();
                System.out.println("a user has joined! ");
                ClientHandler clientHandler=new ClientHandler(s);
                Thread thread=new Thread(clientHandler);
                thread.start();
            }

        }catch (IOException e){
            closeEverything();
        }

    }


    private void closeEverything() {
        try {
            if (ss!=null){
                ss.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(6669);
        Server server=new Server(serverSocket);
        server.startServer();
    }
}
