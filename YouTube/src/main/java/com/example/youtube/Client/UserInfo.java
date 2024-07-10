package com.example.youtube.Client;

import org.json.JSONObject;

public class UserInfo {
    public UserInfo() {

    }

    private static JSONObject info;

    public JSONObject getInfo() {

        //System.out.println(info);
        return info;
    }

    public void setInfo(JSONObject info) {
        this.info = info;
        //System.out.println(info);

    }

}
