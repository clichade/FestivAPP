package com.example.trjano.festivapp.data

import android.content.Context
import android.util.EventLog
import com.example.trjano.festivapp.data.database.AppDatabase
import com.example.trjano.festivapp.data.database.EventDAO
import com.example.trjano.festivapp.data.database.EventItem
import com.example.trjano.festivapp.data.network.SongKickAPI
import java.util.ArrayList
import kotlin.concurrent.thread

class EventRepository private constructor(context: Context){

    val eventDAO: EventDAO = AppDatabase.getDatabase(context.getApplicationContext()).eventDAO()
    val songKickAPI: SongKickAPI = SongKickAPI

    companion object : SingletonHolder<EventRepository, Context>(::EventRepository)

    /**
     * Gets all favorite events from database source
     * @return list events
     */
    fun getAllFavorites(): List<EventItem> {

        var list = emptyList<EventItem>()
        thread { list = eventDAO.getAllFavorites()}
        return list

    }

    /**
     * Gets all past events from database source
     * @return list events
     */
    fun getAllPastEvents(): List<EventItem> {
        var list = emptyList<EventItem>()
        thread { list = eventDAO.getAllPastEvents()}
        return list
    }

    /**
     * Gets all upcoming events from database source
     * @return list events
     */
    fun getAllUpcomingEvents(): List<EventItem> {
        var list = emptyList<EventItem>()
        thread { list = eventDAO.getAllUpcomingEvents()}
        return list
    }

    /**
     * Deletes all past events from database source
     */
    fun deleteAllPastEvents() = thread { eventDAO.deleteAllPastEvents()}


    /**
     * Deletes all upcoming events from database source
     */
    fun deleteAllUpcomingEvents() = thread { eventDAO.deleteAllUpcomingEvents()}

    /**
     * Deletes all favorite events from database source
     */
    fun deleteAllFavorites() = thread { eventDAO.deleteAllFavorites()}

    /**
     * Inserts an event into database
     * @param event
     * @return long ID from event
     */
    fun insertEvent(event: EventItem): Long? {
        return eventDAO.insertEvent(event)
    }

    /**
     * Deletes an event from database
     * @param event
     */
    fun deleteEvent(event: EventItem) = thread { eventDAO.deleteEvent(event)}


    /**
     * Gets an event from database source
     * @param id from event
     * @return EventItem
     */
    fun getEvent(id: Long?): EventItem {
        return eventDAO.getEvent(id!!)
    }

    /**
     * Gets all events that take place in one location (networking)
     * @param location
     * @return List events
     */
    fun find(location: String): ArrayList<EventItem> {
        var list = ArrayList<EventItem>()
        thread { list = songKickAPI.find(location) }
        return list
    }

    /**
     * Gets all events by name and location (networking)
     * @param location
     * @param name
     * @return
     */
    fun find(location: String, name: String): ArrayList<EventItem> {
        var list = ArrayList<EventItem>()
        thread { list = songKickAPI.find(location, name)}
        return list

    }

    /**
     * Dado el nombre de una ciudad de España, realiza una llamada a la API de SongKick
     * y devuelve su ID.
     * En caso de que no es encuentre el resultado será -1
     * @param city
     * @return
     */
    fun getCityID(city: String): String {
        var city = ""
        thread { city =  SongKickAPI.getCityID(city) }
        return city
    }
}