package com.example.didyouknow.pojo;

import java.util.HashMap;
import java.util.Map;

public class EntityData {
    public String title;
    public boolean isFavorite;
    public Map<String, String> data = new HashMap<String, String>();
    public Map<String, String> infoData = new HashMap<String, String>();
    public String backgroundText;
    public String imageURL;
    public long longitude;
    public long latitude;

    public EntityData(String title, boolean isFavorite, Map<String, String> data, Map<String, String> infoData, String backgroundText, String imageURL, long longitude, long latitude) {
        this.title = title;
        this.isFavorite = isFavorite;
        this.data = data;
        this.infoData = infoData;
        this.backgroundText = backgroundText;
        this.imageURL = imageURL;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
