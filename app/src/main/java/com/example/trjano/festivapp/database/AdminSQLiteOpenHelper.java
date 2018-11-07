package com.example.trjano.festivapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "events.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_FAVORITES =
            "CREATE TABLE " + DBContract.EventItem.TABLE_NAME_FAVORITES + " (" +
                    DBContract.EventItem._ID + " INTEGER PRIMARY KEY," +
                    DBContract.EventItem.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    DBContract.EventItem.COLUMN_NAME_CITY + TEXT_TYPE + COMMA_SEP +
                    DBContract.EventItem.COLUMN_NAME_START_DATE + TEXT_TYPE + COMMA_SEP +
                    DBContract.EventItem.COLUMN_NAME_END_DATE + TEXT_TYPE + COMMA_SEP+
                    DBContract.EventItem.COLUMN_NAME_LOCATION + TEXT_TYPE + COMMA_SEP+
                    DBContract.EventItem.COLUMN_NAME_ARTISTS + TEXT_TYPE + COMMA_SEP+
                    DBContract.EventItem.COLUMN_NAME_TYPE + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_UPCOMING =
            "CREATE TABLE " + DBContract.EventItem.TABLE_NAME_UPCOMING + " (" +
                    DBContract.EventItem._ID + " INTEGER PRIMARY KEY," +
                    DBContract.EventItem.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    DBContract.EventItem.COLUMN_NAME_CITY + TEXT_TYPE + COMMA_SEP +
                    DBContract.EventItem.COLUMN_NAME_START_DATE + TEXT_TYPE + COMMA_SEP +
                    DBContract.EventItem.COLUMN_NAME_END_DATE + TEXT_TYPE + COMMA_SEP+
                    DBContract.EventItem.COLUMN_NAME_LOCATION + TEXT_TYPE + COMMA_SEP+
                    DBContract.EventItem.COLUMN_NAME_ARTISTS + TEXT_TYPE + COMMA_SEP+
                    DBContract.EventItem.COLUMN_NAME_TYPE + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_PAST =
            "CREATE TABLE " + DBContract.EventItem.TABLE_NAME_PAST + " (" +
                    DBContract.EventItem._ID + " INTEGER PRIMARY KEY," +
                    DBContract.EventItem.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    DBContract.EventItem.COLUMN_NAME_CITY + TEXT_TYPE + COMMA_SEP +
                    DBContract.EventItem.COLUMN_NAME_START_DATE + TEXT_TYPE + COMMA_SEP +
                    DBContract.EventItem.COLUMN_NAME_END_DATE + TEXT_TYPE + COMMA_SEP+
                    DBContract.EventItem.COLUMN_NAME_LOCATION + TEXT_TYPE + COMMA_SEP+
                    DBContract.EventItem.COLUMN_NAME_ARTISTS + TEXT_TYPE + COMMA_SEP+
                    DBContract.EventItem.COLUMN_NAME_TYPE + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_FAVORITES_EVENTS =
            "DROP TABLE IF EXISTS " + DBContract.EventItem.TABLE_NAME_FAVORITES;
    private static final String SQL_DELETE_UPCOMING_EVENTS =
            "DROP TABLE IF EXISTS " + DBContract.EventItem.TABLE_NAME_UPCOMING;
    private static final String SQL_DELETE_PAST_EVENTS =
            "DROP TABLE IF EXISTS " + DBContract.EventItem.TABLE_NAME_PAST;

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use for locating paths to the the database
     *
     */
    public AdminSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     *     //NOMBRE =NAME
     *     // CIUDAD = CITY NOT NULL
     *     // FECHA INICIO = START_DATE NOT NULL
     *     // FECHA FINAL = END_DATE
     *     // LUGAR EXACTO = LOCATION NOT NULL
     *     // HORA INICIO = START_TIME NOT NULL
     *     // GRUPO = ARTISTS NOT NULL
     *     // CONCIERTO/EVENTO = TYPE NOT NULL INTEGER
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FAVORITES);
        db.execSQL(SQL_CREATE_UPCOMING);
        db.execSQL(SQL_CREATE_PAST);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_FAVORITES_EVENTS);
        db.execSQL(SQL_DELETE_PAST_EVENTS);
        db.execSQL(SQL_DELETE_UPCOMING_EVENTS);
        onCreate(db);
    }
}
