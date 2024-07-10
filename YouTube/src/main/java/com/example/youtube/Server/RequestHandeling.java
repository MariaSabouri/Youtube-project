package com.example.youtube.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

public class RequestHandeling {
    public static JSONObject JsonHanldler(JSONObject jsonObject) {
        if (jsonObject.getString("Class").equals("database")){
            String databasefunction=jsonObject.getString("DataManager");

            if (Objects.equals(databasefunction, "LogIn")){
                Boolean b=expextedDatabase.LogIn(jsonObject.getString("Parameter1"),jsonObject.getString("Parameter2"));
                String jsonString = "{\"Class\":\"LoginController\",\"Response\":\""+ b +"\" }";
                return new JSONObject(jsonString) ;

            } else if (Objects.equals(databasefunction, "SignUp")) {
                Boolean b=expextedDatabase.SignUp(jsonObject.getString("Parameter1"),jsonObject.getString("Parameter2"),jsonObject.getString("Parameter3"));
                String jsonString = "{\"Class\":\"SignUpController\",\"Response\":\""+ b +"\" }";
                return new JSONObject(jsonString);

            } else if (Objects.equals(databasefunction, "SearchingVideo")) {
                JSONArray ResultVideo= expextedDatabase.resultVideoFromSearchbar(jsonObject.getString("Parameter1"));
                String jsonString = "{\"Class\":\"commonToolSearchBar\",\"Response\":\""+ ResultVideo +"\" }";
                JSONObject JsonObject=new JSONObject();
                JsonObject.put("Class","commonToolSearchBar");
                JsonObject.put("Response",ResultVideo);
                return JsonObject;

            }else if (Objects.equals(databasefunction, "VPCIDInfo")) {
                JSONObject VPCResult=expextedDatabase.VPCIDInfo(jsonObject.getString("Parameter1"),jsonObject.getString("Parameter2"));
                JSONObject jsonObject1=new JSONObject();
                jsonObject1.put("Class","CommonTools/goVideoView");
                jsonObject1.put("Response",VPCResult);
                return jsonObject1;

            }else if (Objects.equals(databasefunction,"gettingUserInfo")) {
                JSONObject UserInfo=expextedDatabase.gettingUserInfo(jsonObject.getString("Parameter1"));
                JSONObject jsonObject1=new JSONObject();
                jsonObject1.put("Class","CommonTools/setUserInfo");
                jsonObject1.put("Response",UserInfo);
                return jsonObject1;


            } else if (Objects.equals(databasefunction,"CreatingChannel")) {
                Boolean b=expextedDatabase.CreatingChannel(jsonObject.getString("Parameter1"),jsonObject.getString("Parameter2"));
                JSONObject jsonObject1=new JSONObject();
                jsonObject1.put("Class","CreateChannelController/SettingNameForChannel");
                jsonObject1.put("Response",b);
                return jsonObject1;

            } else if (Objects.equals(databasefunction,"CreatingPlaylist")) {
                Boolean b=expextedDatabase.CreatingPlaylist(jsonObject.getString("Parameter1"),jsonObject.getString("Parameter2"));
                JSONObject jsonObject1=new JSONObject();
                jsonObject1.put("Class","ChannelPlaylistsController/SettingNameForPlaylist");
                jsonObject1.put("Response",b);
                return jsonObject1;

            } else if (Objects.equals(databasefunction,"TrendVPCIDForHomePage")) {
                JSONArray jsonArray=expextedDatabase.TrendVPCIDForHomePage();
                JSONObject jsonObject1=new JSONObject();
                jsonObject1.put("Class","HomePageController/setHomepageTrendVideos");
                jsonObject1.put("Response",jsonArray);
                return jsonObject1;

            } else if (Objects.equals(databasefunction,"gettingAllUserVPCID")) {
                JSONArray jsonArray=expextedDatabase.gettingAllUserVPCID(jsonObject.getString("Parameter1"),jsonObject.getString("Parameter2"));
                JSONObject jsonObject1=new JSONObject();
                jsonObject1.put("Class","ChannelController/VideosForThisPlaylist");
                jsonObject1.put("Response",jsonArray);
                return jsonObject1;

            } else if (Objects.equals(databasefunction,"ViewCounterForVPCID")) {
                expextedDatabase.ViewCounterForVPCID(jsonObject.getString("Parameter1"),jsonObject.getString("Parameter2"));
                JSONObject jsonObject1=new JSONObject();
                jsonObject1.put("Class","CommonTools/updateViewsNubmerForVideo");
                return jsonObject1;

            } else if (Objects.equals(databasefunction,"getUserLikeAndDislikeAction")) {
                JSONObject jsonObject1=expextedDatabase.getUserLikeAndDislikeAction(jsonObject.getString("Parameter1"),jsonObject.getString("Parameter2"));
                JSONObject jsonObject2=new JSONObject();
                jsonObject2.put("Class","CommonTools/setLikeAndDislikeStatistics");
                jsonObject2.put("Response",jsonObject1);
                System.out.println("sended");
                return jsonObject2;

            } else if (Objects.equals(databasefunction,"UpdateLikeAndDislikeActionsOnDataBase")) {
                expextedDatabase.UpdateLikeAndDislikeActionsOnDataBase(jsonObject.getJSONObject("Parameter1"));
                return  null;
            }
        }






        else if (jsonObject.getString("Class").equals("videoHandeling")) {
            String videoHandelingFuctions=jsonObject.getString("videoHandelingFuctions");

            if (Objects.equals(videoHandelingFuctions,"createUserFolder")){
                videoHandeling.createUserFolder(jsonObject.getString("Parameter1"));
                return null;

            } else if (Objects.equals(videoHandelingFuctions,"createUserPlaylistFolder")) {
                videoHandeling.createUserPlaylistFolder(jsonObject.getString("Parameter1"),jsonObject.getString("Parameter2"));
                return null;

            } else if (Objects.equals(videoHandelingFuctions,"UploadVideo")) {
                videoHandeling.UploadVideo(jsonObject.getString("Parameter1"),jsonObject.getString("Parameter2"),jsonObject.getString("Parameter3"),jsonObject.getString("Parameter4"));
                expextedDatabase.uploadindVideo(jsonObject.getString("Parameter1"),jsonObject.getString("Parameter2"),jsonObject.getString("Parameter3"));
                JSONObject UserInfo=expextedDatabase.gettingUserInfo(jsonObject.getString("Parameter1"));
                JSONObject jsonObject1=new JSONObject();
                jsonObject1.put("Class","CommonTools/setUserInfo");
                jsonObject1.put("Response",UserInfo);
                return jsonObject1;

            } else if (Objects.equals(videoHandelingFuctions,"gettingVideoViaViedoNamePlaylistNamePublisher")) {
                String Video=videoHandeling.gettingVideoViaViedoNamePlaylistNamePublisher(jsonObject.getString("Parameter1"),jsonObject.getString("Parameter2"),jsonObject.getString("Parameter3"));
                JSONObject jsonObject1=new JSONObject();
                jsonObject1.put("Class","CommonTools/getVideo");
                jsonObject1.put("Response",Video);
                System.out.println("bebin:"+jsonObject1);
                return jsonObject1;


            }

        }


        return null;


    }
}
