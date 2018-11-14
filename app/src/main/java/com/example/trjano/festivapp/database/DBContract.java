package com.example.trjano.festivapp.database;

import android.provider.BaseColumns;

public final class DBContract {
    private DBContract() {}

    public static class EventItem implements BaseColumns {
        public static final String TABLE_NAME_FAVORITES = "FAVORITES_EVENTS";
        public static final String TABLE_NAME_UPCOMING = "UPCOMING_EVENTS";
        public static final String TABLE_NAME_PAST = "PAST_EVENTS";

        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_CITY = "city";
        public static final String COLUMN_NAME_START_DATE = "start_date";
        public static final String COLUMN_NAME_LOCATION = "location";
        public static final String COLUMN_NAME_ARTISTS = "artists";
        public static final String COLUMN_NAME_TYPE = "type";

    }
}
