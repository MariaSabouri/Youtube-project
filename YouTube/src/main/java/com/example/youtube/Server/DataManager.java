package com.example.youtube.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;


public class DataManager {
    //When Calling openConnection method,the connection with database will be connected
    public static void openConnection(){
        //TODO
    }


    /**
     * In SignUp method,we insert new user to "UserTable" table in database
     * In this function, it is also checked if such a username exists in the database; if so, "false" output is sent else
     * "true" is sent.
     */

    public static Boolean SignUp(String Username,String email,String Password){
        //TODO
        return false;
    }
    /**
     * this method returns "true" if this user exist;else it returns false
     * @param password this parameter is converting to Integer for fetching data in database
     */

    public static Boolean LogIn(String username,String password){
        //TODO
        return true;
    }
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

    public static ArrayList<JSONObject> TrendVPCIDForHomePage(){
        //[{"VPCID":---,"VideoName":---,"ChannelName":---,"NumberOfView":---},{},{},...]
        return null;}

    public static JSONObject VPCIDInfo(String VPCID,String Username){
        //{"VideoName":---,"ChannelName":---,"NumberOfView":---,"NumberOfLike":---,"NumberOfDislike":---,"DateOfIllustration":---,"LikeOfThisUser":---,"DislikeOfThisUser":---
        // ,"Comments":[{messageId(With No reply To any messageId):[list of messageIds which reply this messageId]},{},{},...]}
        return null;
    }
    public static String GettingCommentViaMessageId(String messageId){
        return null;
    }
    public static void InsertingNewMessageForAVPC(String VPCID,String username,String MessageIdToReply,String Message){}
    public static void creatingARowForAUserWhoWhatchAVPCIn_ATable_(String username,String VPCID){}
    public static void LikeDislikeActionForAVPC(String Username,String VPCID,Boolean Like,Boolean Dislike){
        //for example if user clicks on like,we change bollean status of like column
    }

    public static ArrayList<JSONObject> playlistOfUser(String Username){
        //[{"PUID":---,"PUName":---},{},{},---]
        return null;}
    public static void CreatNewPlaylistForAUser(String Username,String PUName){}
    public static void insertAVPCIDToUserPlaylist(String PUID,String VPCID){}
    public static ArrayList<String> ListingVPCIDOf_UserVideoPlaylist(String PUID){
        return null;}


    public static void CreatingAChannel(String Username,String ChannelName){}
    public static void CreatingAplaylistForAChannel(String ChannelName,String PCName){}
    public static void InsertingVideoTo_VideoTable(String Username,String videoName){
        //Note that For A User,any two VideoNames Shouldn't have the same name
    }
    public static void UploadingVideoForAChannelInSpecialPlaylist(String Username,String VideoName,String PCID){
        InsertingVideoTo_VideoTable(Username,VideoName);
        //From The VideoTable we get The VID
    }
    public static ArrayList<String> GettingVPCIDFromAPlaylistOfAChannel(String PCID){return null;}





}
