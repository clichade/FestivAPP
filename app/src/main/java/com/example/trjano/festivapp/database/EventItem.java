package com.example.trjano.festivapp.database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = DBContract.EventItem.TABLE_NAME)
public class EventItem implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long _id;

    @ColumnInfo(name = DBContract.EventItem.NAME)
    private String mName;

    @ColumnInfo(name = DBContract.EventItem.CITY)
    private String mCity;

    @ColumnInfo(name = DBContract.EventItem.START_DATE)
    private String mStartDate;

    @ColumnInfo(name = DBContract.EventItem.LOCATION)
    private String mLocation;

    @ColumnInfo(name = DBContract.EventItem.ARTISTS)
    private String mArtists;

    @ColumnInfo(name = DBContract.EventItem.TYPE)
    private String mType;

    @ColumnInfo(name = DBContract.EventItem.FAVORITE)
    private int mFavorite;

    @ColumnInfo(name = DBContract.EventItem.UPCOMING)
    private int mUpcoming;

    @ColumnInfo(name = DBContract.EventItem.ASSISTED)
    private int mAssisted;

    public EventItem(long id, String name, String city, String startDate, String location, String artists, String type) {
        this._id = id;
        this.mName = name;
        this.mCity = city;
        this.mStartDate=startDate;
        this.mLocation=location;
        this.mArtists=artists;
        this.mType=type;
        this.mFavorite = 0;
        this.mUpcoming = 0;
        this.mAssisted = 0;
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
    public int getmFavorite() {
        return mFavorite;
    }

    public void setmFavorite(int mFavorite) {
        this.mFavorite = mFavorite;
    }

    public int getmUpcoming() {
        return mUpcoming;
    }

    public void setmUpcoming(int mUpcoming) {
        this.mUpcoming = mUpcoming;
    }

    public int getmAssisted() {
        return mAssisted;
    }

    public void setmAssisted(int mUpcoming) {
        this.mAssisted = mUpcoming;
    }
}
