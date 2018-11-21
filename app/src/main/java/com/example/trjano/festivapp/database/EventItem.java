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
    private String name;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @ColumnInfo(name = DBContract.EventItem.CITY)
    private String city;

    @ColumnInfo(name = DBContract.EventItem.START_DATE)
    private String startDate;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getUpcoming() {
        return upcoming;
    }

    public void setUpcoming(int upcoming) {
        this.upcoming = upcoming;
    }

    public int getAssisted() {
        return assisted;
    }

    public void setAssisted(int assisted) {
        this.assisted = assisted;
    }

    @ColumnInfo(name = DBContract.EventItem.LOCATION)
    private String location;

    @ColumnInfo(name = DBContract.EventItem.ARTISTS)
    private String artists;

    @ColumnInfo(name = DBContract.EventItem.TYPE)
    private String type;

    @ColumnInfo(name = DBContract.EventItem.FAVORITE)
    private int favorite;

    @ColumnInfo(name = DBContract.EventItem.UPCOMING)
    private int upcoming;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ColumnInfo(name = DBContract.EventItem.ASSISTED)
    private int assisted;

    public EventItem(long id, String name, String city, String startDate, String location, String artists, String type) {
        this._id = id;
        this.name = name;
        this.city = city;
        this.startDate=startDate;
        this.location=location;
        this.artists=artists;
        this.type=type;
        this.favorite = 0;
        this.upcoming = 0;
        this.assisted = 0;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }
}
