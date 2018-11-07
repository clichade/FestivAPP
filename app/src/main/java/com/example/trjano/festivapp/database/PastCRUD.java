package com.example.trjano.festivapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PastCRUD {
    private AdminSQLiteOpenHelper mDbHelper;
    private static PastCRUD mInstance;

    private PastCRUD(Context context){
        mDbHelper = new AdminSQLiteOpenHelper(context);
    }

    /**
     * Singleton Implementation
     * @param context
     * @return the instance of FavoritesCRUD
     */
    public static PastCRUD getInstance(Context context){
        if (mInstance==null)
            mInstance = new PastCRUD(context);
        return mInstance;
    }
    public List<EventItem> getAllPastEvents(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DBContract.EventItem._ID,
                DBContract.EventItem.COLUMN_NAME_NAME,
                DBContract.EventItem.COLUMN_NAME_CITY,
                DBContract.EventItem.COLUMN_NAME_START_DATE,
                DBContract.EventItem.COLUMN_NAME_END_DATE,
                DBContract.EventItem.COLUMN_NAME_LOCATION,
                DBContract.EventItem.COLUMN_NAME_ARTISTS,
                DBContract.EventItem.COLUMN_NAME_TYPE
        };
        String selection = null;
        String[] selectionArgs = null;

        String sortOrder = null;

        Cursor cursor = db.query(
                DBContract.EventItem.TABLE_NAME_PAST,           // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        ArrayList<EventItem> items = new ArrayList<>();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                items.add(getToDoItemFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;


    }

    public long insertPast(EventItem item){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DBContract.EventItem.COLUMN_NAME_NAME, item.getmName());
        values.put(DBContract.EventItem.COLUMN_NAME_CITY, item.getmCity());
        values.put(DBContract.EventItem.COLUMN_NAME_START_DATE, item.getmStartDate());
        values.put(DBContract.EventItem.COLUMN_NAME_END_DATE, item.getmEndDate());
        values.put(DBContract.EventItem.COLUMN_NAME_LOCATION, item.getmLocation());
        values.put(DBContract.EventItem.COLUMN_NAME_ARTISTS, item.getmArtists());
        values.put(DBContract.EventItem.COLUMN_NAME_TYPE, item.getmType());


        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DBContract.EventItem.TABLE_NAME_PAST, null, values);

        return newRowId;
    }

    public void deleteAllPastEvents() {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Define 'where' part of query.
        String selection = null;
        // Specify arguments in placeholder order.
        String[] selectionArgs = null;

        // Issue SQL statement.
        db.delete(DBContract.EventItem.TABLE_NAME_PAST, selection, selectionArgs);
    }

    public void close(){
        if (mDbHelper!=null) mDbHelper.close();
    }

    public static EventItem getToDoItemFromCursor(Cursor cursor) {

        long ID = cursor.getInt(cursor.getColumnIndex(DBContract.EventItem._ID));
        String name = cursor.getString(cursor.getColumnIndex(DBContract.EventItem.COLUMN_NAME_NAME));
        String city = cursor.getString(cursor.getColumnIndex(DBContract.EventItem.COLUMN_NAME_CITY));
        String startDate = cursor.getString(cursor.getColumnIndex(DBContract.EventItem.COLUMN_NAME_START_DATE));
        String endDate = cursor.getString(cursor.getColumnIndex(DBContract.EventItem.COLUMN_NAME_END_DATE));
        String location = cursor.getString(cursor.getColumnIndex(DBContract.EventItem.COLUMN_NAME_LOCATION));
        String artists = cursor.getString(cursor.getColumnIndex(DBContract.EventItem.COLUMN_NAME_ARTISTS));
        String type = cursor.getString(cursor.getColumnIndex(DBContract.EventItem.COLUMN_NAME_TYPE));


        EventItem item = new EventItem(ID,name,city,startDate,endDate,location,artists,type);

        Log.d("PastCRUD",item.toLog());

        return item;
    }
}
