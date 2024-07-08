package com.example.youtube.Server;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Base64;

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
            System.out.println("create a folder for"+username);

        }catch (IOException e){
            System.err.println("Failed to create directory: " + e.getMessage());
        }

    }
    public static void createUserPlaylistFolder(String username,String PlaylistName){
        Path path=Paths.get(basePath,username);
        Path path1=Paths.get(String.valueOf(path),PlaylistName);

        try {
            Files.createDirectories(path1);
            System.out.println("create a playlist folder for"+username);

        }catch (IOException e){
            System.err.println("Failed to create directory: " + e.getMessage());
        }

    }


    public static void UploadVideo(String username, String playlistName, String fileName, String fileContentBase64) {
        try {
            byte[] fileContentBytes = Base64.getDecoder().decode(fileContentBase64);
            String filePath = basePath + "\\" + username + "\\" + playlistName + "\\" + fileName;

            // Ensure the directory exists
            File directory = new File(basePath + "\\" + username + "\\" + playlistName);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                fileOutputStream.write(fileContentBytes);
            }

            System.out.println("File saved: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
