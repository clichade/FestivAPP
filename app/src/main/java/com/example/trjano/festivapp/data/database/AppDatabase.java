package com.example.trjano.festivapp.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * App Database. It contains only one table with events that
 * are favorite and/or upcoming and/or assisted
 *
 * @see https://developer.android.com/training/data-storage/room/
 * <p>
 * Database tag must contain:
 * - Entities with all entities that should be in database.
 * - Version = 1
 */
@Database(entities = {EventItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    /**Singleton instance for Database*/
    private static AppDatabase instance;

    /**
     * Accesses the Database. Only DAO must call it
     * @param context
     * @return AppDatabase
     */
    public static AppDatabase getDatabase (Context context){
        if(instance== null)
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DBContract.EventItem.DATABASE_NAME).build();
        return instance;
    }

    /**
     * Accesses database operations through DAO pattern
     * @return EventDAO
     */
    public abstract EventDAO eventDAO();
}
