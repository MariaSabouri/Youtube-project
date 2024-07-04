package com.example.youtube.Server;

import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class RequestHandeling {
    public static String JsonHanldler(JSONObject jsonObject) {
        String databasefunction=jsonObject.getString("DataManager");
//        System.out.println(databasefunction);
        if (Objects.equals(databasefunction, "LogIn")){
            Boolean b=DataManager.LogIn(jsonObject.getString("Parameter1"),jsonObject.getString("Parameter2"));
            String jsonString = "{\"Class\":\"LoginController\",\"Response\":\""+ b +"\" }";
            System.out.println(jsonString);
            return jsonString;
        } else if (Objects.equals(databasefunction, "SignUp")) {
            Boolean b=DataManager.SignUp(jsonObject.getString("Parameter1"),jsonObject.getString("Parameter2"),jsonObject.getString("Parameter3"));
            String jsonString = "{\"Class\":\"SignUpController\",\"Response\":\""+ b +"\" }";
            System.out.println(jsonString);
            return jsonString;
        }
        return null;


    }
}
