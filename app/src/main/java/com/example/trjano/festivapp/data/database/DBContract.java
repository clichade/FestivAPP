package com.example.trjano.festivapp.data.database;

import android.provider.BaseColumns;

public final class DBContract {
    private DBContract() {}

    public static class EventItem implements BaseColumns {
        public static final String TABLE_NAME= "EVENTS";
        public static final String DATABASE_NAME = "events.db";
        public static final String FAVORITE = "favorite";
        public static final String UPCOMING = "upcoming";
        public static final String ASSISTED = "assisted";


        public static final String NAME = "name";
        public static final String CITY = "city";
        public static final String START_DATE = "start_date";
        public static final String LOCATION= "location";
        public static final String ARTISTS = "artists";
        public static final String TYPE = "type";


    }
}
