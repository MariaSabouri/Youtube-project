package com.example.youtube.Server;

import java.io.*;
import java.net.Socket;
import java.util.Vector;

public class ClientHandler implements Runnable{
    Socket s;
    String username;
    BufferedWriter bufferedWriter;
    BufferedReader bufferedReader;

    // Arraylist to store active clients
    static Vector<ClientHandler> ar = new Vector<>();
    // constructor
    public ClientHandler(Socket s) {
        this.s=s;
        try {
            this.bufferedReader=new BufferedReader(new InputStreamReader(s.getInputStream()));
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            this.username=bufferedReader.readLine();
            ar.add(this);


        }catch (IOException e){
            closeEverything(s,bufferedReader,bufferedWriter);
        }

    }

    @Override
    public void run() {


    }


    private void closeEverything(Socket s,BufferedReader bufferedReader,BufferedWriter bufferedWriter) {
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
