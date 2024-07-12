package com.example.youtube.Server;

//import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.*;

public class DataManager {
    //When Calling openConnection method,the connection with database will be connected
    private static Connection conn;
    public static void openConnection(){
        try {
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String user = "postgres";
            String password = "Faezeh@84";

            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Database connection successful.");
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
        }
    }

    /**
     * In SignUp method,we insert new user to "UserTable" table in database
     * In this function, it is also checked if such a username exists in the database; if so, "false" output is sent else
     * "true" is sent.
     */
    public static Boolean SignUp(String Name, String Username, String Password) {
        try (PreparedStatement checkUsernameStmt = conn.prepareStatement("SELECT COUNT(*) FROM UserTable WHERE username = ?");
             PreparedStatement checkNameStmt = conn.prepareStatement("SELECT COUNT(*) FROM UserTable WHERE name = ?")) {
            checkUsernameStmt.setString(1, Username);
            try (ResultSet checkUsernameResult = checkUsernameStmt.executeQuery()) {
                if (checkUsernameResult.next() && checkUsernameResult.getInt(1) > 0) {
                    System.err.println("Error: Username already exists.");
                    return false;
                }
            }
            checkNameStmt.setString(1, Name);
            try (ResultSet checkNameResult = checkNameStmt.executeQuery()) {
                if (checkNameResult.next() && checkNameResult.getInt(1) > 0) {
                    System.err.println("Error: Name already exists.");
                    return false;
                }
            }
            try (PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO UserTable (username, Password, name) VALUES (?, ?, ?)")) {
                insertStmt.setString(1, Username);
                insertStmt.setInt(2, Integer.parseInt(Password)); // Assuming Password is stored as plain text or appropriately hashed
                insertStmt.setString(3, Name);
                int rowsAffected = insertStmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User created successfully.");
                    return true; // User successfully created
                } else {
                    System.err.println("Error: Unable to create user.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during signup: " + e.getMessage());
            return false;
        }
    }

    /**
     * this method returns "true" if this user exist;else it returns false
     * @param password this parameter is converting to Integer for fetching data in database
     */

    public static Boolean LogIn(String username, String password) {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM UserTable WHERE username = ? AND Password = ?")) {
            stmt.setString(1, username);
            stmt.setInt(2, Integer.parseInt(password));

            try (ResultSet result = stmt.executeQuery()) {
                if (result.next() && result.getInt(1) > 0) {
                    System.out.println("Login successful.");
                    return true; // User exists
                } else {
                    System.out.println("Login failed: Invalid username or password.");
                    return false; // User does not exist
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during login: " + e.getMessage());
        }
       // Return null if there's an error
        return true;
    }

    public static JSONObject gettingUserInfo(String username){
        JSONObject jsonObject=new JSONObject();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM UserTable WHERE username = ?")) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    jsonObject.put("Username", rs.getString("username"));
                    jsonObject.put("ChannelName", rs.getString("channel"));
                    jsonObject.put("Subscribers", rs.getString("numberOfSubscriptions"));
                    JSONArray PlaylistNamejsonArray=GettingJsonArrayOfPlaylistNames(username);
                    jsonObject.put("Playlists",PlaylistNamejsonArray);
                    return jsonObject;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user info: " + e.getMessage());
        }
        return null;
    }
    private static JSONArray SubscriptionsJsonArray(String username) {
        JSONArray jsonArray = new JSONArray();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT channelname FROM public.\"subscriptionsTable\" WHERE username = ?")) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    JSONObject subscription = new JSONObject();
                    subscription.put("ChannelName", rs.getString("channelname"));
                    jsonArray.put(subscription);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching subscriptions: " + e.getMessage());
        }
        return jsonArray;
    }

    private static JSONArray GettingJsonArrayOfPlaylistNames(String username) {
        JSONArray jsonArray=new JSONArray();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT PCName FROM ChannelPlaylistsTable WHERE UserTable_UName = ?")) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String playlistName = rs.getString("PCName");
                    jsonArray.put(playlistName);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching playlist names: " + e.getMessage());
        }
        return jsonArray;
    }
    public static Boolean CreatingChannel(String username, String channelName) {
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE UserTable SET channel = ? WHERE username = ?")) {
            stmt.setString(1, channelName);
            stmt.setString(2, username);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Channel created successfully.");
                return true;
            } else {
                System.err.println("Error: Unable to create channel.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error creating channel: " + e.getMessage());
            return false;
        }
    }
    //////
    public static Boolean CreatingPlaylist(String username, String playlistName) {
        UUID uuid = UUID.randomUUID();
        int id = uuid.hashCode();
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO channelplayliststable (UserTable_UName, PCName, pcid) VALUES (?, ?, ?)")) {
            stmt.setString(1, username);
            stmt.setString(2, playlistName);
            stmt.setInt(3, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Playlist created successfully.");
                return true;
            } else {
                System.err.println("Error: Unable to create playlist.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error creating playlist: " + e.getMessage());
            return false;
        }
    }
    public static JSONArray gettingAllVPCI(String channelName,String playlistName){
        //[{"VPCID":---,"VideoName":---,"ChannelName":---,"NumberOfView":---},{},{},...]
        JSONArray jsonArray=new JSONArray();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT VPCId, VName, channel, videotable.\"numberOfview\" " +
                        "FROM PlaylistChannelVideoTable " +
                        "JOIN ChannelPlaylistsTable ON PlaylistChannelVideoTable.ChannelPlaylistsTable_PCId = ChannelPlaylistsTable.PCId " +
                        "JOIN VideoTable ON PlaylistChannelVideoTable.VideoTable_VId = VideoTable.VId " +
                        "join usertable on videotable.usertable_uname = usertable.username " +
                        "WHERE usertable.channel = ? AND ChannelPlaylistsTable.PCName = ?")) {
            stmt.setString(1, channelName);
            stmt.setString(2, playlistName);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    JSONObject video = new JSONObject();
                    video.put("VPCID", rs.getInt("VPCId"));
                    video.put("VideoName", rs.getString("VideoName"));
                    video.put("ChannelName", rs.getString("ChannelName"));
                    video.put("NumberOfView", rs.getInt("NumberOfView"));
                    jsonArray.put(video);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching videos from channel playlist: " + e.getMessage());
        }
        return jsonArray;
    }
    public static JSONArray gettingAllUserVPCID(String Username,String playlistName){
        //[{"VPCID":---,"VideoName":---,"ChannelName":---,"NumberOfView":---},{},{},...]
        JSONArray jsonArray = new JSONArray();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM playlistchannelvideotable WHERE username = ? AND \"playlistName\" = ?")) {
            stmt.setString(1, Username);
            stmt.setString(2, playlistName);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    JSONObject video = new JSONObject();
                    video.put("VPCID", rs.getInt("VPCId"));
                    video.put("VideoName", rs.getString("VideoName"));
                    video.put("ChannelName", getChannelName(Username));
                    video.put("NumberOfView", rs.getInt("numberOfview"));
                    jsonArray.put(video);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching videos from user playlist: " + e.getMessage());
        }
        return jsonArray;
    }
///
    public static void uploadindVideo(String Username, String PlaylistName, String VideoName) {
        UUID uuid = UUID.randomUUID();
        int id = uuid.hashCode();
        try (PreparedStatement insertVideoStmt = conn.prepareStatement(
                        "INSERT INTO playlistChannelvideotable (username, \"videoName\", \"playlistName\", vpcid) VALUES (?, ?, ?, ?)")) {
            //insertVideoStmt.setInt(1, generateNewVPCID());
            insertVideoStmt.setString(1, Username);
            insertVideoStmt.setString(2, VideoName);
            insertVideoStmt.setString(3, PlaylistName);
            insertVideoStmt.setInt(4, id);
            insertVideoStmt.executeUpdate();
            System.out.println("Video inserted into VideoTable.");
        } catch (SQLException e) {
            System.err.println("Error inserting video: " + e.getMessage());
            return;
        }
    }
    ///
    public static void ViewCounterForVPCID(String VPCID, String Username) {
        // Add this User to UserLikeAndDislikeAction (if not already present)
//        try (PreparedStatement stmt = conn.prepareStatement(
//                "INSERT INTO UserLikeAndDislikeAction (UserTable_UName, UserTable_ChannelName, PlaylistChannelVideoTable_VPCId, Like, Dislike) " +
//                        "VALUES (?, ?, ?, FALSE, FALSE) " + // Set Like and Dislike to FALSE initially
//                        "ON DUPLICATE KEY UPDATE Like = VALUES(Like), Dislike = VALUES(Dislike)")) {
//            stmt.setString(1, Username);
//            stmt.setString(2, Username); // Assuming Username is also the channel name
//            stmt.setInt(3, Integer.parseInt(VPCID)); // Assuming VPCID is a string, convert to integer
//
//            stmt.executeUpdate();
//            System.out.println("User added to UserLikeAndDislikeAction.");

            // Increment the view count in PlaylistChannelVideoTable
            try (PreparedStatement updateStmt = conn.prepareStatement(
                    "UPDATE PlaylistChannelVideoTable SET \"numberOfview\" = \"numberOfview\" + 1 WHERE VPCId = ?")) {
                updateStmt.setInt(1, Integer.parseInt(VPCID));
                updateStmt.executeUpdate();
                System.out.println("View count incremented.");
            //}
        } catch (SQLException e) {
            System.err.println("Error updating view count: " + e.getMessage());
        }
    }
    public static JSONArray resultVideoFromSearchbar(String videoname){
        //[{"VPCID":---,"VideoName":---,"ChannelName":---,"NumberOfView":---},{},{},...]
        JSONArray jsonArray = new JSONArray();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM PlaylistChannelVideoTable " +
                        "join usertable on PlaylistChannelVideoTable.username = usertable.username "+
                        "WHERE \"videoName\" LIKE ?")) {
            stmt.setString(1, "%" + videoname + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    JSONObject video = new JSONObject();
                    video.put("VPCID", rs.getInt("vpcid"));
                    video.put("VideoName", rs.getString("VideoName"));
                    video.put("ChannelName", rs.getString("channel"));
                    video.put("NumberOfView", rs.getInt("NumberOfView"));
                    jsonArray.put(video);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching search results: " + e.getMessage());
        }
        return jsonArray;
    }
    public static JSONArray TrendVPCIDForHomePage(){
        //give top 9 trend Video:
        //[{"VPCID":---,"VideoName":---,"ChannelName":---,"NumberOfView":---},{},{},...]
        JSONArray jsonArray = new JSONArray();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM PlaylistChannelVideoTable ORDER BY PlaylistChannelVideoTable.\"numberOfview\" DESC LIMIT 9")) { // Get top 9 trending videos

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    JSONObject video = new JSONObject();
                    video.put("VPCID", rs.getInt("vpcid"));
                    video.put("VideoName", rs.getString("videoName"));
                    video.put("NumberOfView", rs.getInt("numberOfview"));
                    video.put("ChannelName", getChannelName(rs.getString("username")));
                    jsonArray.put(video);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching trending videos: " + e.getMessage());
        }
        return jsonArray;
    }
    public static String getChannelName(String username) {
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT channel FROM usertable WHERE username = ?")) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    return rs.getString("channel");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching channelName: " + e.getMessage());
        }
        return null;
    }
    public static JSONObject VPCIDInfo(String VPCID,String Username){
        //{"VideoName":---,"ChannelName":---,"NumberOfView":---,"NumberOfLike":---,"NumberOfDislike":---,"DateOfIllustration":---,"LikeOfThisUser":---,"DislikeOfThisUser":---
        //  },{},{},...]}
        ////{"Username":---,"VPCID":---,"VideoName":---,"ChannelName":---,"PlaylistName":---,"NumberOfView":---,"NumberOfLike":---,"NumberOfDislike":---,"DateOfIllustration":---,"LikeOfThisUser":---,"DislikeOfThisUser":---
        //// ,"Comments":[{messageId(With No reply To any messageId):[list of messageIds which reply this messageId]},{},{},...]}

        JSONObject videoInfo = new JSONObject();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PlaylistChannelVideoTable WHERE VPCId = ?")) {
            stmt.setInt(1, Integer.parseInt(VPCID));

            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    videoInfo.put("Username", result.getString("username"));
                    videoInfo.put("VPCID", VPCID);
                    videoInfo.put("VideoName", result.getString("videoName"));
                    videoInfo.put("PlaylistName", result.getString("playlistName"));
                    videoInfo.put("ChannelName", getChannelName(Username));
                    videoInfo.put("NumberOfView", result.getInt("NumberOfView"));
                    videoInfo.put("DateOfIllustration", result.getDate("DateOfIllustration"));
                    // Check if user has liked or disliked the video
                    try (PreparedStatement likeDislikeStmt = conn.prepareStatement("SELECT * FROM UserLikeAndDislikeAction WHERE PlaylistChannelVideoTable_VPCId = ? AND UserTable_UName = ?")) {
                        likeDislikeStmt.setInt(1, Integer.parseInt(VPCID));
                        likeDislikeStmt.setString(2, Username);
                        try (ResultSet likeDislikeResult = likeDislikeStmt.executeQuery()) {
                            if (likeDislikeResult.next()) {
                                videoInfo.put("LikeOfThisUser", likeDislikeResult.getBoolean("Like"));
                                videoInfo.put("DislikeOfThisUser", likeDislikeResult.getBoolean("Dislike"));
                            } else {
                                videoInfo.put("LikeOfThisUser", false);
                                videoInfo.put("DislikeOfThisUser", false);
                            }
                        }
                    }
                    try (PreparedStatement likeDislikeStmt = conn.prepareStatement(
                            "SELECT COUNT(CASE WHEN \"Like\" = TRUE THEN 1 END) AS likeCount, " +
                                    "       COUNT(CASE WHEN \"dislike\" = TRUE THEN 1 END) AS dislikeCount " +
                                    "FROM UserLikeAndDislikeAction " +
                                    "WHERE PlaylistChannelVideoTable_VPCId = ?")) {
                        likeDislikeStmt.setInt(1, Integer.parseInt(VPCID));
                        try (ResultSet likeDislikeResult = likeDislikeStmt.executeQuery()) {
                            if (likeDislikeResult.next()) {
                                videoInfo.put("NumberOfLike", likeDislikeResult.getInt("likeCount"));
                                videoInfo.put("NumberOfDislike", likeDislikeResult.getInt("dislikeCount"));
                            } else {
                                videoInfo.put("NumberOfLike", false);
                                videoInfo.put("NumberOfDislike", false);
                            }
                        }
                    }

                    return videoInfo;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching VPCID info: " + e.getMessage());
        }
        return null;
    }

    public static void CreatingAChannel(String Username,String ChannelName){
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE UserTable SET ChannelName = ? WHERE UName = ?")) {
            stmt.setString(1, ChannelName);
            stmt.setString(2, Username);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Channel created successfully.");
            } else {
                System.err.println("Error: Unable to create channel.");
            }
        } catch (SQLException e) {
            System.err.println("Error creating channel: " + e.getMessage());
        }
    }
    public static boolean hasLikedOrDisliked(String userName, int vpcid) {
        try (PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM UserLikeAndDislikeAction WHERE UserTable_UName = ? and PlaylistChannelVideoTable_VPCId = ? ")) {
            checkStmt.setString(1, userName);
            checkStmt.setInt(2, vpcid);
            try (ResultSet checkResult = checkStmt.executeQuery()) {
                if (checkResult.next() && checkResult.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking for existing video: " + e.getMessage());
            return false;
        }
        return false;
    }
    //give channel name
    public static void UpdateLikeAndDislikeActionsOnDataBase(JSONObject jsonObject){
        //jsonObject is like this:
        //{"VPCID":22,"UserName":"ryan","Like":--,"DisLike":--}
        //you should update UserLikeAndDislikeAction table
        if (!hasLikedOrDisliked(jsonObject.getString("UserName"), jsonObject.getInt("VPCID"))) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO UserLikeAndDislikeAction (UserTable_UName, PlaylistChannelVideoTable_VPCId, \"Like\", \"dislike\") " +
                            "VALUES (?, ?, ?, ?) ")) {
                stmt.setString(1, jsonObject.getString("UserName"));
                stmt.setInt(2, jsonObject.getInt("VPCID"));
                stmt.setBoolean(3, jsonObject.getBoolean("Like"));
                stmt.setBoolean(4, jsonObject.getBoolean("dislike"));

                stmt.executeUpdate();
                System.out.println("Like/Dislike action updated.");
            } catch (SQLException e) {
                System.err.println("Error updating like/dislike action: " + e.getMessage());
            }
        }
        else if (jsonObject.getBoolean("Like")){
            try (PreparedStatement stmt = conn.prepareStatement("UPDATE UserLikeAndDislikeAction SET  \"Like\" = ? WHERE UserTable_UName = ? and PlaylistChannelVideoTable_VPCId = ?")) {
                stmt.setBoolean(1, true);
                stmt.setString(2, jsonObject.getString("UserName"));
                stmt.setInt(3, jsonObject.getInt("VPCID"));
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        else {
            try (PreparedStatement stmt = conn.prepareStatement("UPDATE UserLikeAndDislikeAction SET  \"dislike\" = ? WHERE UserTable_UName = ? and PlaylistChannelVideoTable_VPCId = ?")) {
                stmt.setBoolean(1, true);
                stmt.setString(2, jsonObject.getString("UserName"));
                stmt.setInt(3, jsonObject.getInt("VPCID"));
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    public static void main(String args[]) {
        openConnection();
        //SignUp("faezeh", "faezeh_kh", "1234");
        //System.out.println(LogIn("ll", "1234"));
        //System.out.println(CreatingChannel("faezeh_kh", "faezeh'ss channel"));;
        //System.out.println(CreatingPlaylist("faezeh_kh" ,"faezeh'ss playlist"));
        //uploadindVideo("faezeh_kh", "faezeh'ss playlist", "faezeh'sss video");
        //System.out.println(GettingJsonArrayOfPlaylistNames("faezeh_kh"));
        //System.out.println(gettingUserInfo("faezeh_kh"));
        //System.out.println(getChannelName("faezeh_kh"));
        //System.out.println(VPCIDInfo("-1696819317", "faezeh_kh"));
        //UpdateLikeAndDislikeActionsOnDataBase();
        //System.out.println(hasLikedOrDislked("faezeh_kh", -1696819317));
        //System.out.println(TrendVPCIDForHomePage());
        //System.out.println(resultVideoFromSearchbar("La"));
        //System.out.println(gettingAllUserVPCID("faezeh_kh", "faezeh'ss playlist"));
        //ViewCounterForVPCID("-1696819317", "faezeh_kh");
        System.out.println(SubscriptionsJsonArray("d"));
    }
}

