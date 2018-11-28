package com.example.trjano.festivapp.data;

import android.arch.lifecycle.LiveData;

import com.example.trjano.festivapp.data.database.EventDAO;
import com.example.trjano.festivapp.data.database.EventItem;
import com.example.trjano.festivapp.data.network.SongKickAPI;

public class EventRepository {
    private static final String LOG_TAG = EventRepository.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static EventRepository sInstance;
    private final EventDAO eventDAO;
    private final SongKickAPI songKickAPI;

    private EventRepository(EventDAO eventDAO, SongKickAPI songKickAPI){
        this.eventDAO = eventDAO;
        this.songKickAPI = songKickAPI;


    }
}
