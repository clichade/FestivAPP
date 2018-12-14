package com.example.trjano.festivapp.data.database;

import android.provider.BaseColumns;

/**
 * This class have all the names (made by agreement) to database elements
 */
public final class DBContract {
    private DBContract() {}

    /**
     * BaseColumns is useful to id
     */
    public static class EventItem implements BaseColumns {
        /**Name of the events table*/
        public static final String TABLE_NAME = "EVENTS";

        /**Name given to database*/
        public static final String DATABASE_NAME = "events.db";

        /**Name given to Event attributes*/
        public static final String SONGKICK_ID = "songkick_ID";
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
