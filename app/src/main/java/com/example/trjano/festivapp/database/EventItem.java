package com.example.trjano.festivapp.database;

public class EventItem {
    public final static String ID = "ID";
    public final static String NAME = "name";
    public final static String CITY = "city";
    public final static String START_DATE = "start_date";
    public final static String END_DATE = "end_date";
    public final static String LOCATION = "location";
    public final static String ARTISTS = "artists";
    public final static String TYPE = "type";

    private long mId;
    private String mName;
    private String mCity;
    private String mStartDate;
    private String mEndDate;
    private String mLocation;
    private String mArtists;
    private String mType;

    public EventItem(long id, String name, String city, String startDate, String endDate, String location, String artists, String type) {
        this.mId=id;
        this.mName = name;
        this.mCity = city;
        this.mStartDate=startDate;
        this.mEndDate=endDate;
        this.mLocation=location;
        this.mArtists=artists;
        this.mType=type;
    }

    public EventItem() {
        this.mId=-1;
        this.mName = "default";
        this.mCity = "default";
        this.mStartDate="default";
        this.mEndDate="default";
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

    public String getmEndDate() {
        return mEndDate;
    }

    public void setmEndDate(String mEndDate) {
        this.mEndDate = mEndDate;
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
                "End date:"+mEndDate+ITEM_SEP+"Location:"+mLocation+ITEM_SEP+
                "Artists:"+mArtists+ITEM_SEP+"Type:"+mType;
    }
    @Override
    public String toString() {
        return "EventItem{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mCity='" + mCity + '\'' +
                ", mStartDate='" + mStartDate + '\'' +
                ", mEndDate='" + mEndDate + '\'' +
                ", mLocation='" + mLocation + '\'' +
                ", mArtists='" + mArtists + '\'' +
                ", mType='" + mType + '\'' +
                '}';
    }
}
