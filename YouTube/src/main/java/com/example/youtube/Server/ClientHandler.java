package com.example.youtube.Server;

import java.io.*;
import java.net.Socket;
import java.util.Vector;
import org.json.JSONObject;


public class ClientHandler implements Runnable{
    Socket s;
    BufferedWriter bufferedWriter;
    BufferedReader bufferedReader;

    // constructor
    public ClientHandler(Socket s) {
        this.s=s;
        try {
            this.bufferedReader=new BufferedReader(new InputStreamReader(s.getInputStream()));
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

        }catch (IOException e){
            closeEverything(s,bufferedReader,bufferedWriter);
        }

    }

    @Override
    public void run() {
        while (s.isConnected()){
            try {
                String read=bufferedReader.readLine();
                JSONObject jsonObject = new JSONObject(read);
//                System.out.println(jsonObject);
                JSONObject jsonObject1 = RequestHandeling.JsonHanldler(jsonObject);
                if (jsonObject1!=null){
                    bufferedWriter.write(jsonObject1.toString());
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }

            }catch (IOException e){
                closeEverything(s,bufferedReader,bufferedWriter);
                break;
            }

        }


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
