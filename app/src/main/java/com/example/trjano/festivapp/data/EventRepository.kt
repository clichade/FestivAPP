package com.example.trjano.festivapp.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.EventLog
import com.example.trjano.festivapp.data.database.AppDatabase
import com.example.trjano.festivapp.data.database.EventDAO
import com.example.trjano.festivapp.data.database.EventItem
import com.example.trjano.festivapp.data.network.SongKickAPI
import java.util.ArrayList
import kotlin.concurrent.thread

class EventRepository private constructor(context: Context){

    /** Todo Aañdir la lógica del funcionamiento de la base de datos
     * Respository:
    Debe encargarse de la lógica interna de añadir, borrar y actualizar
    Ej: AñadirFavoritos: Si existe como Pendiente o Asistido, la tupla del evento se actualizará, si no existe se crea la tupla.
    EliminarFavoritos: Si existe como Pendiente o Asistido. la tupla del evento se actualizará Favorite= 0, si todos están a 0 se eliminará la tupla
    Hacer lo mismo para Pendiente y Asistidos

     */

    val eventDAO: EventDAO = AppDatabase.getDatabase(context.getApplicationContext()).eventDAO()
    val songKickAPI: SongKickAPI = SongKickAPI

    companion object : SingletonHolder<EventRepository, Context>(::EventRepository)

    /**
     * Gets all favorite events from database source
     * @return list events
     */
    fun getAllFavorites(): LiveData<List<EventItem>> = eventDAO.getAllFavorites()



    /**
     * Gets all past events from database source
     * @return list events
     */
    fun getAllPastEvents(): LiveData<List<EventItem>> = eventDAO.getAllPastEvents()

    /**
     * Gets all upcoming events from database source
     * @return list events
     */
    fun getAllUpcomingEvents(): LiveData<List<EventItem>> = eventDAO.getAllUpcomingEvents()


    /**
     * Deletes all past events from database source
     */
    fun deleteAllPastEvents() = eventDAO.deleteAllPastEvents()

    /**
     * Deletes all upcoming events from database source
     */
    fun deleteAllUpcomingEvents() = eventDAO.deleteAllUpcomingEvents()

    /**
     * Deletes all favorite events from database source
     */
    fun deleteAllFavorites() =  eventDAO.deleteAllFavorites()

    /**
     * Inserts an event into database
     * @param event
     * @return long ID from event
     */
    fun insertEvent(event: LiveData<EventItem>): Long? = eventDAO.insertEvent(event)

    /**
     * Deletes an event from database
     * @param event
     */
    fun deleteEvent(event: LiveData<EventItem>) = eventDAO.deleteEvent(event)

    /**
     * Gets an event from database source
     * @param id from event
     * @return EventItem
     */
    fun getEvent(id: Long?): LiveData<EventItem> = eventDAO.getEvent(id!!)


    /**
     * Gets all events that take place in one location (networking)
     * @param location
     * @return List events
     */
    fun find(location: String): LiveData<ArrayList<EventItem>> {
        var v = MutableLiveData<ArrayList<EventItem>>()
        v.value = songKickAPI.find(location)
        return v
    }

    /**
     * Gets all events by name and location (networking)
     * @param location
     * @param name
     * @return
     */
    fun find(location: String, name: String): LiveData<ArrayList<EventItem>> {
        var v = MutableLiveData<ArrayList<EventItem>>()
        v.value =  songKickAPI.find(location, name)
        return v

    }


}