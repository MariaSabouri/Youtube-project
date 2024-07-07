package com.example.youtube.Server;

//import org.json.JSONObject;
import org.json.JSONArray;

import java.sql.*;
import java.util.*;

public class DataManager {
    //When Calling openConnection method,the connection with database will be connected
    private static Connection conn; // Static variable to hold the connection
    public static void openConnection(){
        //TODO
        try {
            // Replace with your actual database connection details
            String url = "jdbc:postgresql://localhost:5432/postgres"; // PostgreSQL connection URL
            String user = "postgres"; // Your PostgreSQL username
            String password = "Faezeh@84"; // Your PostgreSQL password

            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Database connection successful.");
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
        }
    }

    public static void InsertingVideoTo_VideoTable(String Username,String videoName){
        // Note that for a user, any two VideoNames shouldn't have the same name
        UUID myUuid = UUID.randomUUID();
        int hashedInt = myUuid.hashCode();
        // Store hashedInt in an INT column in your database
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO VideoTable (VId, UserTable_UName, VName) VALUES (?, ?, ?)")) {

            stmt.setInt(1, hashedInt);
            stmt.setString(2, Username);
            stmt.setString(3, videoName);
            System.out.println(stmt.toString());
            // Check if a video with the same name already exists for the user
            try (PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM VideoTable WHERE UserTable_UName = ? AND VName = ?")) {
                checkStmt.setString(1, Username);
                checkStmt.setString(2, videoName);
                try (ResultSet checkResult = checkStmt.executeQuery()) {
                    if (checkResult.next() && checkResult.getInt(1) > 0) {
                        System.err.println("Error: A video with the same name already exists for this user.");
                        return; // Exit the function if a duplicate is found
                    }
                }
            }

            // If no duplicate found, insert the video
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Video inserted successfully.");
            } else {
                System.err.println("Error: Unable to insert video.");
            }
        } catch (SQLException e) {
            System.err.println("Error inserting video: " + e.getMessage());
        }
    }
    /**
     * In SignUp method,we insert new user to "UserTable" table in database
     * In this function, it is also checked if such a username exists in the database; if so, "false" output is sent else
     * "true" is sent.
     */
///////INTEGER PASSWORD
    public static Boolean SignUp(String Username,String email, int Password){
        //TODO
        try (PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM UserTable WHERE UName = ?")) {
            checkStmt.setString(1, Username);
            try (ResultSet checkResult = checkStmt.executeQuery()) {
                if (checkResult.next() && checkResult.getInt(1) > 0) {
                    System.err.println("Error: Username already exists.");
                    return false; // Username already exists
                } else {
                    try (PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO UserTable (UName, Pass,channelName) VALUES (?, ?, ?)")) {
                        insertStmt.setString(1, Username);
                        insertStmt.setInt(2, Password);
                        insertStmt.setString(3, email);
                        int rowsAffected = insertStmt.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("User created successfully.");
                            return true; // User successfully created
                        } else {
                            System.err.println("Error: Unable to create user.");
                            return false;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during signup: " + e.getMessage());
        }
        return null; // Return null if there's an error
        //return false;
    }
    /**
     * this method returns "true" if this user exist;else it returns false
     * @param password this parameter is converting to Integer for fetching data in database
     */

    public static Boolean LogIn(String username, int password) {
        //TODO
        try (PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM UserTable WHERE UName = ? AND Pass = ?")) {
            stmt.setString(1, username);
            stmt.setInt(2, password); // Assuming password is a string, convert to integer

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

    //    public static ArrayList<JSONObject> TrendVPCIDForHomePage(){
//        //[{"VPCID":---,"VideoName":---,"ChannelName":---,"NumberOfView":---},{},{},...]
//        ArrayList<JSONObject> trendVideos = new ArrayList<>();
//        try (PreparedStatement stmt = conn.prepareStatement("SELECT VPCId, VideoName, ChannelName, NumberOfView FROM PlaylistChannelVideoTable ORDER BY NumberOfView DESC LIMIT 10")) {
//            try (ResultSet result = stmt.executeQuery()) {
//                while (result.next()) {
//                    JSONObject video = new JSONObject();
//                    video.put("VPCID", result.getInt("VPCId"));
//                    video.put("VideoName", result.getString("VideoName"));
//                    video.put("ChannelName", result.getString("ChannelName"));
//                    video.put("NumberOfView", result.getInt("NumberOfView"));
//                    trendVideos.add(video);
//                }
//            }
//        } catch (SQLException e) {
//            System.err.println("Error fetching trending videos: " + e.getMessage());
//        }
//        return trendVideos;
//        }
//    public static JSONObject VPCIDInfo(String VPCID,String Username){
//        //{"VideoName":---,"ChannelName":---,"NumberOfView":---,"NumberOfLike":---,"NumberOfDislike":---,"DateOfIllustration":---,"LikeOfThisUser":---,"DislikeOfThisUser":---
//        // ,"Comments":[{messageId(With No reply To any messageId):[list of messageIds which reply this messageId]},{},{},...]}
//        JSONObject videoInfo = new JSONObject();
//        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM PlaylistChannelVideoTable WHERE VPCId = ?")) {
//            stmt.setInt(1, Integer.parseInt(VPCID)); // Assuming VPCID is a string, convert to integer
//
//            try (ResultSet result = stmt.executeQuery()) {
//                if (result.next()) {
//                    videoInfo.put("VideoName", result.getString("VideoName"));
//                    videoInfo.put("ChannelName", result.getString("ChannelName"));
//                    videoInfo.put("NumberOfView", result.getInt("NumberOfView"));
//                    videoInfo.put("NumberOfLike", result.getInt("NumberOfLike"));
//                    videoInfo.put("NumberOfDislike", result.getInt("NumberOfDislike"));
//                    videoInfo.put("DateOfIllustration", result.getDate("DateOfIllustration"));
//
//                    // Get comments
//                    try (PreparedStatement commentStmt = conn.prepareStatement("SELECT * FROM CommentsTable WHERE PlaylistChannelVideoTable_VPCId = ?")) {
//                        commentStmt.setInt(1, Integer.parseInt(VPCID));
//                        try (ResultSet commentResult = commentStmt.executeQuery()) {
//                            JSONArray comments = new JSONArray();
//                            while (commentResult.next()) {
//                                JSONObject comment = new JSONObject();
//                                comment.put("messageId", commentResult.getInt("MessageId"));
//                                comment.put("Message", commentResult.getString("Message"));
//                                comment.put("ReplyTo", commentResult.getInt("CommentsTable_MessageId"));
//                                comments.add(comment);
//                            }
//                            videoInfo.put("Comments", comments);
//                        }
//                    }
//
//                    // Check if user has liked or disliked the video
//                    try (PreparedStatement likeDislikeStmt = conn.prepareStatement("SELECT * FROM UserLikeAndDislikeAction WHERE PlaylistChannelVideoTable_VPCId = ? AND UserTable_UName = ?")) {
//                        likeDislikeStmt.setInt(1, Integer.parseInt(VPCID));
//                        likeDislikeStmt.setString(2, Username);
//                        try (ResultSet likeDislikeResult = likeDislikeStmt.executeQuery()) {
//                            if (likeDislikeResult.next()) {
//                                videoInfo.put("LikeOfThisUser", likeDislikeResult.getBoolean("Like"));
//                                videoInfo.put("DislikeOfThisUser", likeDislikeResult.getBoolean("Dislike"));
//                            } else {
//                                videoInfo.put("LikeOfThisUser", false);
//                                videoInfo.put("DislikeOfThisUser", false);
//                            }
//                        }
//                    }
//
//                    return videoInfo;
//                }
//            }
//        } catch (SQLException e) {
//            System.err.println("Error fetching VPCID info: " + e.getMessage());
//        }
//        return null;
//    }
    public static JSONArray resultVideoFromSearchbar(String videoname){
        //[{"VPCID":---,"VideoName":---,"ChannelName":---,"NumberOfView":---},{},{},...]
        Map<String, Object> o1=new HashMap<String,Object>();
        o1.put("VPCID",23);
        o1.put("VideoName","alla");
        o1.put("ChannelName","sddsfd");
        o1.put("NumberOfView",2837);
        Map<String, Object> o2=new HashMap<String,Object>();
        o2.put("VPCID",27);
        o2.put("VideoName","afa");
        o2.put("ChannelName","fgfd");
        o2.put("NumberOfView",287);
        List<Map<String, Object>> response=new ArrayList<>();
        response.add(o1);
        response.add(o2);
        JSONArray jsonArray=new JSONArray(response);


        return jsonArray;
    }
    public static String GettingCommentViaMessageId(String messageId){
        try (PreparedStatement stmt = conn.prepareStatement("SELECT Message FROM CommentsTable WHERE MessageId = ?")) {
            stmt.setInt(1, Integer.parseInt(messageId)); // Assuming messageId is a string, convert to integer

            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    return result.getString("Message");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching comment: " + e.getMessage());
        }
        return null; // Return null if there's an error or comment not found
    }
    public static void InsertingNewMessageForAVPC(String VPCID, String username, String MessageIdToReply, String Message) {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO CommentsTable (PlaylistChannelVideoTable_VPCId, UserTable_UName, UserTable_ChannelName, Message, CommentsTable_MessageId) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setInt(1, Integer.parseInt(VPCID)); // Assuming VPCID is a string, convert to integer
            stmt.setString(2, username);
            stmt.setString(3, username); // Assuming username is also the channel name
            stmt.setString(4, Message);
            stmt.setInt(5, MessageIdToReply == null ? null : Integer.parseInt(MessageIdToReply)); // Convert MessageIdToReply to integer if not null

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Comment inserted successfully.");
            } else {
                System.err.println("Error: Unable to insert comment.");
            }
        } catch (SQLException e) {
            System.err.println("Error inserting comment: " + e.getMessage());
        }
    }
    public static void creatingARowForAUserWhoWhatchAVPCIn_ATable_(String username,String VPCID){
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO UserWatchHistory (Username, VPCID) VALUES (?, ?)")) {
            stmt.setString(1, username);
            stmt.setInt(2, Integer.parseInt(VPCID)); // Assuming VPCID is a string, convert to integer

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Watch history updated.");
            } else {
                System.err.println("Error: Unable to update watch history.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating watch history: " + e.getMessage());
        }
    }
    public static void LikeDislikeActionForAVPC(String Username,String VPCID,Boolean Like,Boolean Dislike){
        // For example, if the user clicks on like, we change the boolean status of the like column.
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE PlaylistChannelVideoTable SET NumberOfLike = NumberOfLike + ?, NumberOfDislike = NumberOfDislike + ? WHERE VPCID = ?")) {
            stmt.setInt(1, Like ? 1 : 0); // Increment NumberOfLike if Like is true, otherwise 0
            stmt.setInt(2, Dislike ? 1 : 0); // Increment NumberOfDislike if Dislike is true, otherwise 0
            stmt.setInt(3, Integer.parseInt(VPCID)); // Assuming VPCID is a string, convert to integer

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Like/Dislike action updated.");

                // Update UserLikeAndDislikeAction table
                try (PreparedStatement updateLikeDislikeStmt = conn.prepareStatement("INSERT INTO UserLikeAndDislikeAction (UserTable_UName, UserTable_ChannelName, PlaylistChannelVideoTable_VPCId, Like, Dislike) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE Like = ?, Dislike = ?")) {
                    updateLikeDislikeStmt.setString(1, Username);
                    updateLikeDislikeStmt.setString(2, Username); // Assuming Username is also the channel name
                    updateLikeDislikeStmt.setInt(3, Integer.parseInt(VPCID));
                    updateLikeDislikeStmt.setBoolean(4, Like);
                    updateLikeDislikeStmt.setBoolean(5, Dislike);
                    updateLikeDislikeStmt.setBoolean(6, Like);
                    updateLikeDislikeStmt.setBoolean(7, Dislike);
                    updateLikeDislikeStmt.executeUpdate();
                }
            } else {
                System.err.println("Error: Unable to update like/dislike action.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating like/dislike action: " + e.getMessage());
        }
    }

    //    public static ArrayList<JSONObject> playlistOfUser(String Username){
//        //[{"PUID":---,"PUName":---},{},{},---]
//        return null;
//    }
    public static void CreatNewPlaylistForAUser(String Username,String CName,String PUName){
        UUID myUuid = UUID.randomUUID();
        int hashedInt = myUuid.hashCode();
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO UserPlaylistTable (UserTable_UName, UserTable_ChannelName, PUName, PUID) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, Username);
            stmt.setString(2, CName); // Assuming Username is also the channel name
            stmt.setString(3, PUName);
            stmt.setInt(4,hashedInt);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Playlist created successfully.");
            } else {
                System.err.println("Error: Unable to create playlist.");
            }
        } catch (SQLException e) {
            System.err.println("Error creating playlist: " + e.getMessage());
        }
    }
    public static void insertAVPCIDToUserPlaylist(String PUID,String VPCID){
        UUID myUuid = UUID.randomUUID();
        int hashedInt = myUuid.hashCode();
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO UserVideoPlaylist (UserPlaylistTable_PUId, PlaylistChannelVideoTable_VPCId, VPUID ) VALUES (?, ?, ?)")) {
            stmt.setInt(1, Integer.parseInt(PUID)); // Assuming PUID is a string, convert to integer
            stmt.setInt(2, Integer.parseInt(VPCID)); // Assuming VPCID is a string, convert to integer
            stmt.setInt(3,hashedInt);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Video added to playlist.");
            } else {
                System.err.println("Error: Unable to add video to playlist.");
            }
        } catch (SQLException e) {
            System.err.println("Error adding video to playlist: " + e.getMessage());
        }
    }
    public static ArrayList<String> ListingVPCIDForUserPlaylist(String PUID){
        ArrayList<String> VPCIDs = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT PlaylistChannelVideoTable_VPCId FROM UserVideoPlaylist WHERE UserPlaylistTable_PUId = ?")) {
            stmt.setInt(1, Integer.parseInt(PUID)); // Assuming PUID is a string, convert to integer

            try (ResultSet result = stmt.executeQuery()) {
                while (result.next()) {
                    VPCIDs.add(String.valueOf(result.getInt("PlaylistChannelVideoTable_VPCId")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error listing videos in playlist: " + e.getMessage());
        }
        return VPCIDs;
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
    public static void CreatingAplaylistForAChannel(String ChannelName,String PCName, String UName){
        UUID myUuid = UUID.randomUUID();
        int hashedInt = myUuid.hashCode();
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO ChannelPlaylistsTable (UserTable_UName, UserTable_ChannelName, PCName, PCID) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, UName); // Assuming ChannelName is also the username
            stmt.setString(2, ChannelName);
            stmt.setString(3, PCName);
            stmt.setInt(4,hashedInt);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Channel playlist created successfully.");
            } else {
                System.err.println("Error: Unable to create channel playlist.");
            }
        } catch (SQLException e) {
            System.err.println("Error creating channel playlist: " + e.getMessage());
        }
    }
    public static void UploadingVideoForAChannelInSpecialPlaylist(String Username,String VideoName,String PCID){
        InsertingVideoTo_VideoTable(Username, VideoName);
        // From the VideoTable, we get the VID
        try (PreparedStatement stmt = conn.prepareStatement("SELECT VId FROM VideoTable WHERE UserTable_UName = ? AND VName = ?")) {
            stmt.setString(1, Username);
            stmt.setString(2, VideoName);

            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    int VID = result.getInt("VId");

                    // Insert the video into the PlaylistChannelVideoTable
                    try (PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO PlaylistChannelVideoTable (ChannelPlaylistsTable_PCId, VideoTable_VId) VALUES (?, ?)")) {
                        insertStmt.setInt(1, Integer.parseInt(PCID)); // Assuming PCID is a string, convert to integer
                        insertStmt.setInt(2, VID);

                        int rowsAffected = insertStmt.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Video uploaded to channel playlist.");
                        } else {
                            System.err.println("Error: Unable to upload video to channel playlist.");
                        }
                    }
                } else {
                    System.err.println("Error: Video not found in VideoTable.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error uploading video to channel playlist: " + e.getMessage());
        }
    }
    public static ArrayList<String> GettingVPCIDFromAPlaylistOfAChannel(String PCID){
        ArrayList<String> VPCIDs = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT VPCId FROM PlaylistChannelVideoTable WHERE ChannelPlaylistsTable_PCId = ?")) {
            stmt.setInt(1, Integer.parseInt(PCID)); // Assuming PCID is a string, convert to integer

            try (ResultSet result = stmt.executeQuery()) {
                while (result.next()) {
                    VPCIDs.add(String.valueOf(result.getInt("VPCId")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching videos from channel playlist: " + e.getMessage());
        }
        return VPCIDs;
    }
    public static void main(String args[]) {
        openConnection();
        //InsertingVideoTo_VideoTable("l","String VideoName");
        //System.out.println(LogIn("l",99));
        //InsertingNewMessageForAVPC("VPCID", String username, String MessageIdToReply, String Message)
        //CreatingAplaylistForAChannel("ll","ss", "l");
        //CreatNewPlaylistForAUser("l","ll","hh");
        //insertAVPCIDToUserPlaylist("11","22");
       // ListingVPCIDForUserPlaylist("192577626");
        CreatingAChannel("p","lll");
    }




}

