package com.example.trjano.festivapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class FavoritesCRUD {
    private AdminSQLiteOpenHelper mDbHelper;
    private static FavoritesCRUD mInstance;

  private FavoritesCRUD(Context context){
      mDbHelper = new AdminSQLiteOpenHelper(context);
  }

    /**
     * Singleton Implementation
     * @param context
     * @return the instance of FavoritesCRUD
     */
  public static FavoritesCRUD getInstance(Context context){
      if (mInstance==null)
          mInstance = new FavoritesCRUD(context);
      return mInstance;
  }
    public List<EventItem> getAllFavorites(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DBContract.EventItem._ID,
                DBContract.EventItem.COLUMN_NAME_NAME,
                DBContract.EventItem.COLUMN_NAME_CITY,
                DBContract.EventItem.COLUMN_NAME_START_DATE,
                DBContract.EventItem.COLUMN_NAME_LOCATION,
                DBContract.EventItem.COLUMN_NAME_ARTISTS,
                DBContract.EventItem.COLUMN_NAME_TYPE
        };
        String selection = null;
        String[] selectionArgs = null;

        String sortOrder = null;

        Cursor cursor = db.query(
                DBContract.EventItem.TABLE_NAME_FAVORITES,           // The table to query
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
                items.add(getFavoriteFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;


    }


    public EventItem getFavorite(long id){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DBContract.EventItem._ID,
                DBContract.EventItem.COLUMN_NAME_NAME,
                DBContract.EventItem.COLUMN_NAME_CITY,
                DBContract.EventItem.COLUMN_NAME_START_DATE,
                DBContract.EventItem.COLUMN_NAME_LOCATION,
                DBContract.EventItem.COLUMN_NAME_ARTISTS,
                DBContract.EventItem.COLUMN_NAME_TYPE
        };
        String selection = "_id = ?";
        String[] selectionArgs = new String[]{
                String.valueOf(id)
        };

        String sortOrder = null;

        Cursor cursor = db.query(
                DBContract.EventItem.TABLE_NAME_FAVORITES,           // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        EventItem item = new EventItem();

        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            item = getFavoriteFromCursor(cursor);
        }
        cursor.close();
        return item;
    }



    public long insertFavorite(EventItem item){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DBContract.EventItem.COLUMN_NAME_NAME, item.getmName());
        values.put(DBContract.EventItem.COLUMN_NAME_CITY, item.getmCity());
        values.put(DBContract.EventItem.COLUMN_NAME_START_DATE, item.getmStartDate());
        values.put(DBContract.EventItem.COLUMN_NAME_LOCATION, item.getmLocation());
        values.put(DBContract.EventItem.COLUMN_NAME_ARTISTS, item.getmArtists());
        values.put(DBContract.EventItem.COLUMN_NAME_TYPE, item.getmType());


        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DBContract.EventItem.TABLE_NAME_FAVORITES, null, values);

        return newRowId;
    }

    //Deletes the item by argument
    public void deleteFavorite(EventItem item) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Define 'where' part of query.
        String selection = "_id = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = new String[]{
                String.valueOf(item.getmId())
        };

        // Issue SQL statement.
        db.delete(DBContract.EventItem.TABLE_NAME_FAVORITES, selection, selectionArgs);
    }

    public void deleteAllFavorites() {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Define 'where' part of query.
        String selection = null;
        // Specify arguments in placeholder order.
        String[] selectionArgs = null;

        // Issue SQL statement.
        db.delete(DBContract.EventItem.TABLE_NAME_FAVORITES, selection, selectionArgs);
    }

    public void close(){
        if (mDbHelper!=null) mDbHelper.close();
    }

    public static EventItem getFavoriteFromCursor(Cursor cursor) {

        long ID = cursor.getInt(cursor.getColumnIndex(DBContract.EventItem._ID));
        String name = cursor.getString(cursor.getColumnIndex(DBContract.EventItem.COLUMN_NAME_NAME));
        String city = cursor.getString(cursor.getColumnIndex(DBContract.EventItem.COLUMN_NAME_CITY));
        String startDate = cursor.getString(cursor.getColumnIndex(DBContract.EventItem.COLUMN_NAME_START_DATE));
        String location = cursor.getString(cursor.getColumnIndex(DBContract.EventItem.COLUMN_NAME_LOCATION));
        String artists = cursor.getString(cursor.getColumnIndex(DBContract.EventItem.COLUMN_NAME_ARTISTS));
        String type = cursor.getString(cursor.getColumnIndex(DBContract.EventItem.COLUMN_NAME_TYPE));


        EventItem item = new EventItem(ID,name,city,startDate,location,artists,type);

        Log.d("FavoritesCRUD",item.toLog());

        return item;
    }
}
