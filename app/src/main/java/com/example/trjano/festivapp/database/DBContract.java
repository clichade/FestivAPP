package com.example.trjano.festivapp.database;

import android.provider.BaseColumns;

public final class DBContract {
    private DBContract() {}

    public static class EventItem implements BaseColumns {
        public static final String TABLE_NAME_FAVORITES = "FAVORITES_EVENTS";
        public static final String TABLE_NAME_UPCOMING = "UPCOMING_EVENTS";
        public static final String TABLE_NAME_PAST = "PAST_EVENTS";
        public static final String DATABASE_NAME = "events.db";
    }
}
