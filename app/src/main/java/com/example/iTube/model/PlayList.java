package com.example.iTube.model;

import java.io.Serializable;

public class PlayList implements Serializable {

    private int id;
    private int userId;
    private String urlString;

    public PlayList() {

    }

    public PlayList(String urlString) {
        this.urlString = urlString;
    }

    public PlayList(int userId, String urlString) {
        this.userId = userId;
        this.urlString = urlString;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrlString() {
        return urlString;
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }
}