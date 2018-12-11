package com.example.trjano.festivapp.data.database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Architecture Component: Room Entity
 * Event class. It has the information about an event
 *
 * <p>
 * A class labeled with Entity is stored in the AppDatabase as a table
 * It must have:
 * - tableName = the name of the table
 * - PrimaryKey = primary key of the table. Should be autoGenerate = true for insertions
 * - ColumnInfo (opt) = gives another name to the attribute
 */
@Entity(tableName = DBContract.EventItem.TABLE_NAME)
public class EventItem implements Serializable {

    /**Primary key of the table*/
    @PrimaryKey(autoGenerate = true)
    private long _id;

    /**Name of the event*/
    @ColumnInfo(name = DBContract.EventItem.NAME)
    private String name;

    /**City where takes place*/
    @ColumnInfo(name = DBContract.EventItem.CITY)
    private String city;

    /**Start date of the event (DD/MM/YYYY)*/
    @ColumnInfo(name = DBContract.EventItem.START_DATE)
    private String startDate;

    /**Exact location into the city*/
    @ColumnInfo(name = DBContract.EventItem.LOCATION)
    private String location;

    /**Set of artists that perform in the event*/
    @ColumnInfo(name = DBContract.EventItem.ARTISTS)
    private String artists;

    /**Two possible values: festival = 0 or concert = 1*/
    @ColumnInfo(name = DBContract.EventItem.TYPE)
    private String type;

    /**Value 1 means marked as favorite*/
    @ColumnInfo(name = DBContract.EventItem.FAVORITE)
    private int favorite;

    /**Value 1 means marked as upcoming*/
    @ColumnInfo(name = DBContract.EventItem.UPCOMING)
    private int upcoming;

    /**Value 1 means marked as assisted*/
    @ColumnInfo(name = DBContract.EventItem.ASSISTED)
    private int assisted;

    /**
     * Parametrized constructor for Event
     * @param id
     * @param name
     * @param city
     * @param startDate
     * @param location
     * @param artists
     * @param type
     */
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

    /**
     * Returns the city of the event
     *
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns the start date of the event
     *
     * @return startDate (string)
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Returns the exact location into the city of the event
     *
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     * Returns the list of artists that performs in the event
     *
     * @return artists (String)
     */
    public String getArtists() {
        return artists;
    }

    /**
     * Returns 0 or 1 depending if it's festival or concert
     *
     * @return 0 or 1 (String)
     */
    public String getType() {
        return type;
    }

    /**
     * Returns 1 if marked as favorite, else 0
     *
     * @return
     */
    public int getFavorite() {
        return favorite;
    }

    /**
     * Sets the event as favorite
     *
     * @param favorite int (0 or 1)
     */
    public void setFavorite(int favorite) {
        if (favorite == 0 || favorite == 1)
            this.favorite = favorite;
    }

    /**
     * Returns 0 or 1 depending if it's marked as upcoming
     *
     * @return upcoming int
     */
    public int getUpcoming() {
        return upcoming;
    }

    /**
     * Sets the event as upcoming, only 0 or 1
     *
     * @param upcoming int
     */
    public void setUpcoming(int upcoming) {
        if (upcoming == 0 || upcoming == 1)
            this.upcoming = upcoming;
    }

    /**
     * Returns 0 or 1 depending if it's marked as assisted
     *
     * @return assisted int
     */
    public int getAssisted() {
        return assisted;
    }

    /**
     * Sets the event as assisted, only 0 or 1
     *
     * @param assisted
     */
    public void setAssisted(int assisted) {
        if (assisted == 0 || assisted == 1)
            this.assisted = assisted;
    }

    /**
     * Returns the name of the event
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the event
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the id of the event
     * @return id Long
     */
    public long get_id() {
        return _id;
    }
}
