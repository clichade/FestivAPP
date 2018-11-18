package com.example.trjano.festivapp.eventhierarchy;


import java.io.Serializable;

public class EventItem implements Serializable {
    private long _id;
    private String mName;
    private String mCity;
    private String mStartDate;
    private String mLocation;
    private String mArtists;
    private String mType;

    public EventItem(long id, String name, String city, String startDate, String location, String artists, String type) {
        this._id = id;
        this.mName = name;
        this.mCity = city;
        this.mStartDate=startDate;
        this.mLocation=location;
        this.mArtists=artists;
        this.mType=type;
    }
    public long get_id() { return _id; }
    public String getMName() { return mName; }
    public String getMCity() { return mCity; }
    public String getMStartDate() {
        return mStartDate;
    }
    public String getMLocation() {
        return mLocation;
    }
    public String getMArtists() {
        return mArtists;
    }
    public String getMType() {
        return mType;
    }

}
