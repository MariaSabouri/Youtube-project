package com.example.youtube.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class expextedDatabase {
    public static void openConnection(){
        //TODO
    }
    public static Boolean SignUp(String Username, String Password){
        //if everything is okay
        return true;
        //return true;
        //if somthing goes wrong
//        return false;
    }
    public static JSONObject gettingUserInfo(String username){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("Username","Ali");
        jsonObject.put("ChannelName","asdasd");
        jsonObject.put("Subscribers",23);

        JSONArray PlaylistNamejsonArray=GettingJsonArrayOfPlaylistNames(username);
        jsonObject.put("Playlists",PlaylistNamejsonArray);
      return jsonObject;
    }

    private static JSONArray GettingJsonArrayOfPlaylistNames(String username) {
        JSONArray jsonArray=new JSONArray();
//        jsonArray.put("dormir");
//        jsonArray.put("chanter");
//        jsonArray.put("danser");

        return jsonArray;
    }

    ;
    public static Boolean LogIn(String username, String password) {
        //TODO
        return true;
    }
    public static JSONObject VPCIDInfo(String VPCID, String Username){
        //{"Username":---,"VPCID":---,"VideoName":---,"ChannelName":---,"NumberOfView":---,"NumberOfLike":---,"NumberOfDislike":---,"DateOfIllustration":---,"LikeOfThisUser":---,"DislikeOfThisUser":---
        // ,"Comments":[{messageId(With No reply To any messageId):[list of messageIds which reply this messageId]},{},{},...]}
        JSONObject MainJsonObject=new JSONObject();
        MainJsonObject.put("Username","lala");
        MainJsonObject.put("VPCID",78);
        MainJsonObject.put("VideoName","kjkj");
        MainJsonObject.put("ChannelName","jhdskjsd");
        MainJsonObject.put("NumberOfView",324);
        MainJsonObject.put("NumberOfLike",3);
        MainJsonObject.put("NumberOfDislike",44);
        MainJsonObject.put("DateOfIllustration","jun 2023");
        MainJsonObject.put("LikeOfThisUser",true);
        MainJsonObject.put("DislikeOfThisUser",false);
        MainJsonObject.put("Comments",GetJsonArrayOfGettingTheMessageIDsAndTheMessageIDsThatTheyRepliedTo(VPCID, Username));
        return MainJsonObject;
    }

    private static JSONArray GetJsonArrayOfGettingTheMessageIDsAndTheMessageIDsThatTheyRepliedTo(String vpcid, String username) {
        JSONArray MainjsonArray=new JSONArray();

        JSONObject o1=new JSONObject();
        JSONArray A1=new JSONArray();
        A1.put(1);
        A1.put(2);
        A1.put(3);
        o1.put("2323",A1);

        JSONObject o2=new JSONObject();
        JSONArray A2=new JSONArray();
        A2.put(1);
        A2.put(2);
        A2.put(3);
        o2.put("2323",A2);

        MainjsonArray.put(o1);
        MainjsonArray.put(o2);

        return MainjsonArray;

    }

    public static JSONArray resultVideoFromSearchbar(String videoname){
        //[{"VPCID":---,"VideoName":---,"ChannelName":---,"NumberOfView":---},{},{},...]
        JSONObject o1=new JSONObject();
        o1.put("VPCID",23);
        o1.put("VideoName","alla");
        o1.put("ChannelName","sddsfd");
        o1.put("NumberOfView",2837);
//        o1.put("CapuredImage",)
        JSONObject o2=new JSONObject();
        o2.put("VPCID",27);
        o2.put("VideoName","afa");
        o2.put("ChannelName","fgfd");
        o2.put("NumberOfView",287);
        JSONArray response=new JSONArray();
        response.put(o1);
        response.put(o2);
        JSONArray jsonArray=new JSONArray(response);
        return jsonArray;
    }


    public static Boolean CreatingChannel(String parameter1, String parameter2) {
        return true;
    }

    public static Boolean CreatingPlaylist(String parameter1, String parameter2) {return false;}
}
