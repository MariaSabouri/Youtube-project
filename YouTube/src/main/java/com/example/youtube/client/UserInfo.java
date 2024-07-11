package com.example.youtube.client;

import org.json.JSONObject;

public class UserInfo {
    public UserInfo() {

    }

    private static JSONObject info;

    public JSONObject getInfo() {
        return info;
    }

    public void setInfo(JSONObject info) {
        this.info = info;

    }

}
