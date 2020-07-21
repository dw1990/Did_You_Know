package com.example.didyouknow.adapters;

public class Favorite {
    public String imageURL;
    public String name;

    public Favorite(String imageURL, String name, String date, String id) {
        this.imageURL = imageURL;
        this.name = name;
        this.date = date;
        this.id = id;
    }

    public String date;
    public String id;
}
