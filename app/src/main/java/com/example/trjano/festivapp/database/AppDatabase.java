package com.example.trjano.festivapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.trjano.festivapp.eventhierarchy.FavoriteItem;
import com.example.trjano.festivapp.eventhierarchy.PastItem;
import com.example.trjano.festivapp.eventhierarchy.UpcomingItem;

@Database(entities = {FavoriteItem.class,UpcomingItem.class,PastItem.class}, version = 1)
 public abstract class AppDatabase extends RoomDatabase {
   private static AppDatabase instance;

    /**
     * Singleton
     * @param context
     * @return
     */
   public static AppDatabase getDatabase (Context context){
       if(instance==null)
           instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DBContract.EventItem.DATABASE_NAME).build();
    return instance;
   }
   public abstract EventDAO eventDAO();
}
