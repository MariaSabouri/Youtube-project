package com.example.youtube.Server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

public class RequestHandeling {
    public static JSONObject JsonHanldler(JSONObject jsonObject) {
        String databasefunction=jsonObject.getString("DataManager");
        if (Objects.equals(databasefunction, "LogIn")){
            Boolean b=expextedDatabase.LogIn(jsonObject.getString("Parameter1"),jsonObject.getString("Parameter2"));
            String jsonString = "{\"Class\":\"LoginController\",\"Response\":\""+ b +"\" }";
            return new JSONObject(jsonString) ;
        } else if (Objects.equals(databasefunction, "SignUp")) {
            Boolean b=expextedDatabase.SignUp(jsonObject.getString("Parameter1"),jsonObject.getString("Parameter2"));
            String jsonString = "{\"Class\":\"SignUpController\",\"Response\":\""+ b +"\" }";
            return new JSONObject(jsonString);
        } else if (Objects.equals(databasefunction, "SearchingVideo")) {
            JSONArray ResultVideo= expextedDatabase.resultVideoFromSearchbar(jsonObject.getString("Parameter1"));
            String jsonString = "{\"Class\":\"commonToolSearchBar\",\"Response\":\""+ ResultVideo +"\" }";
            JSONObject JsonObject=new JSONObject();
            JsonObject.put("Class","commonToolSearchBar");
            JsonObject.put("Response",ResultVideo);
            return JsonObject;
        } else if (Objects.equals(databasefunction, "VPCIDInfo")) {
            JSONObject VPCResult=expextedDatabase.VPCIDInfo(jsonObject.getString("Parameter1"),jsonObject.getString("Parameter2"));
            JSONObject jsonObject1=new JSONObject();
            jsonObject1.put("Class","video");
            jsonObject1.put("Response",VPCResult);

            return jsonObject1;

        }
        return null;


    }
}
