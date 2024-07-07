package com.example.youtube.Server;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class videoHandeling {
    private static String basePath="D:\\Maria\\term4\\computer science\\java_programing\\HW\\YoutubeVideos";

    private static String videoIv;
    public static void setVideoIv(String videoId) {
        videoHandeling.videoIv = videoId;
    }


    public static void createUserFolder(String username){
        Path path=Paths.get(basePath,username);
        try {
            Files.createDirectories(path);

        }catch (IOException e){
            System.err.println("Failed to create directory: " + e.getMessage());
        }

    }



}
