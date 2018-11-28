package com.example.trjano.festivapp.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.trjano.festivapp.AppExecutors;
import com.example.trjano.festivapp.data.database.EventDAO;
import com.example.trjano.festivapp.data.database.EventItem;
import com.example.trjano.festivapp.data.network.SongKickAPI;

import java.util.ArrayList;
import java.util.List;

public class EventRepository {
    private static final String LOG_TAG = EventRepository.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static EventRepository sInstance;
    private final EventDAO eventDAO;
    private final SongKickAPI songKickAPI;
    private final AppExecutors mExecutors;

    /**
     * Parametrized constructor
     * @param eventDAO
     * @param songKickAPI
     * @param appExecutors
     */
    private EventRepository(EventDAO eventDAO, SongKickAPI songKickAPI, AppExecutors appExecutors){
        this.eventDAO = eventDAO;
        this.songKickAPI = songKickAPI;
        this.mExecutors = appExecutors;
    }

    /**
     * Singleton
     * @param eventDao
     * @param api
     * @param appExecutors
     * @return
     */
    public synchronized static EventRepository getInstance(EventDAO eventDao,
                                                           SongKickAPI api,
                                                           AppExecutors appExecutors) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new EventRepository(eventDao, api,appExecutors);
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }

    /**
     * Gets all favorite events from database source
     * @return list events
     */
    public List<EventItem> getAllFavorites(){
        return eventDAO.getAllFavorites();
    }

    /**
     * Gets all past events from database source
     * @return list events
     */
    public  List<EventItem> getAllPastEvents(){
        return eventDAO.getAllPastEvents();
    }

    /**
     * Gets all upcoming events from database source
     * @return list events
     */
    public  List<EventItem> getAllUpcomingEvents(){
        return eventDAO.getAllUpcomingEvents();
    }

    /**
     * Deletes all past events from database source
     */
    public void deleteAllPastEvents(){
        eventDAO.deleteAllPastEvents();
    }

    /**
     * Deletes all upcoming events from database source
     */
    public void deleteAllUpcomingEvents(){
        eventDAO.deleteAllUpcomingEvents();
    }

    /**
     * Deletes all favorite events from database source
     */
    public void deleteAllFavorites(){
        eventDAO.deleteAllFavorites();
    }

    /**
     * Inserts an event into database
     * @param event
     * @return long ID from event
     */
    public Long insertEvent(EventItem event){
        return eventDAO.insertEvent(event);
    }

    /**
     * Deletes an event from database
     * @param event
     */
    public void deleteEvent(EventItem event){
        eventDAO.deleteEvent(event);
    }

    /**
     * Gets an event from database source
     * @param id from event
     * @return EventItem
     */
    public EventItem getEvent(Long id){
        return eventDAO.getEvent(id);
    }

    /**
     * Gets all events that take place in one location (networking)
     * @param location
     * @return List events
     */
    public ArrayList<EventItem> find(String location){
        return SongKickAPI.INSTANCE.find(location);
    }

    /**
     * Gets all events by name and location (networking)
     * @param location
     * @param name
     * @return
     */
    public  ArrayList<EventItem> find(String location, String name){
        return SongKickAPI.INSTANCE.find(location,name);
    }

    /**
     * Dado el nombre de una ciudad de España, realiza una llamada a la API de SongKick
     * y devuelve su ID.
     * En caso de que no es encuentre el resultado será -1
     * @param city
     * @return
     */
    public String getCityID(String city){
        return SongKickAPI.INSTANCE.getCityID(city);
    }
}
