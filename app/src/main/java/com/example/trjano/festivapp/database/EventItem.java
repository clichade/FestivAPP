package com.example.trjano.festivapp.database;

import java.io.Serializable;

public class EventItem implements Serializable {
    private long mId;
    private String mName;
    private String mCity;
    private String mStartDate;
    private String mLocation;
    private String mArtists;
    private String mType;

    public EventItem(long id, String name, String city, String startDate, String location, String artists, String type) {
        this.mId=id;
        this.mName = name;
        this.mCity = city;
        this.mStartDate=startDate;
        this.mLocation=location;
        this.mArtists=artists;
        this.mType=type;
    }

    public EventItem() {
        this.mId=-1;
        this.mName = "default";
        this.mCity = "default";
        this.mStartDate="default";
        this.mLocation="default";
        this.mArtists="default";
        this.mType="default";
    }


    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmStartDate() {
        return mStartDate;
    }

    public void setmStartDate(String mStartDate) {
        this.mStartDate = mStartDate;
    }

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public String getmArtists() {
        return mArtists;
    }

    public void setmArtists(String mArtists) {
        this.mArtists = mArtists;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public static final String ITEM_SEP = System.getProperty("line.separator");


    public String toLog() {
        return "ID: "+mId+ ITEM_SEP+"Name:"+mName+ITEM_SEP+
                "City:"+mCity+ITEM_SEP+"Start date:"+mStartDate+ITEM_SEP+
                "Artists:"+mArtists+ITEM_SEP+"Type:"+mType;
    }
    @Override
    public String toString() {
        return "EventItem{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mCity='" + mCity + '\'' +
                ", mStartDate='" + mStartDate + '\'' +
                ", mLocation='" + mLocation + '\'' +
                ", mArtists='" + mArtists + '\'' +
                ", mType='" + mType + '\'' +
                '}';
    }
}
