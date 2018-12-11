package com.example.trjano.festivapp.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.example.trjano.festivapp.data.database.AppDatabase
import com.example.trjano.festivapp.data.database.EventDAO
import com.example.trjano.festivapp.data.database.EventItem
import com.example.trjano.festivapp.data.network.SongKickAPI
import java.util.ArrayList

/**
 * Architecture Component: Repository
 * The repository contains ALL the operations provided from the SongKick api and
 * the database (from DAO)
 * ViewModel only mission is to call necessary methods; Repository manages logic
 * @see https://developer.android.com/jetpack/docs/guide
 */
class EventRepository private constructor(context: Context){

    /**DAO instance, accessed by Singleton*/
    private val eventDAO: EventDAO = AppDatabase.getDatabase(context.applicationContext).eventDAO()

    /**Songkick API (Networking) instance*/
    private val songKickAPI: SongKickAPI = SongKickAPI

    /**Repository is a Singleton too*/
    companion object : SingletonHolder<EventRepository, Context>(::EventRepository)

    /**
     * Gets all favorite events from database source
     * @return list events
     */
    fun getAllFavorites(): LiveData<List<EventItem>> = eventDAO.getAllPastEvents()

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
     * Inserts an favorite event into database if doesn't exists.
     * If exists, it changes to 0.
     * If the event is not marked in any category, the event is deleted
     * @param event
     */
    fun updateFavorite(event: LiveData<EventItem>) {

        //If the event is not favorite, now it is
        if (event.value!!.favorite == 0) {
            event.value!!.favorite = 1

            //If doesn't exists in database, we insert. If exists, we update
            if (eventDAO.getEvent(event.value!!._id).value == null)
                eventDAO.insertEvent(event.value!!)
            else
                eventDAO.updateEvent(event.value!!)

            //If the event is favorited, now it's not
        } else {
            event.value!!.favorite = 0
            eventDAO.updateEvent(event.value!!)

            //Check that event can be erasable from database
            checkErasable(event)
        }
    }

    /**
     * Inserts an upcoming event into database if doesn't exists.
     * If exists, it changes to 0.
     * If the event is not marked in any category, the event is deleted
     * @param event
     */
    fun updateUpcoming(event: LiveData<EventItem>) {

        //If the event is not upcoming, now it is
        if (event.value!!.upcoming == 0) {
            event.value!!.upcoming = 1

            //If doesn't exists in database, we insert. If exists, we update
            if (eventDAO.getEvent(event.value!!._id).value == null)
                eventDAO.insertEvent(event.value!!)
            else
                eventDAO.updateEvent(event.value!!)

            //If the event is upcoming, now it's not
        } else {
            event.value!!.upcoming = 0
            eventDAO.updateEvent(event.value!!)
            //Check that event can be erasable from database
            checkErasable(event)
        }
    }

    /**
     * Inserts an assisted event into database if doesn't exists.
     * If exists, it changes to 0.
     * If the event is not marked in any category, the event is deleted
     * @param event
     */
    fun updateAssisted(event: LiveData<EventItem>) {

        //If the event is not assisted, now it is
        if (event.value!!.assisted == 0) {
            event.value!!.assisted = 1

            //If doesn't exists in database, we insert. If exists, we update
            if (eventDAO.getEvent(event.value!!._id).value == null)
                eventDAO.insertEvent(event.value!!)
            else
                eventDAO.updateEvent(event.value!!)
            //If the event is assisted, now it's not
        } else {
            event.value!!.assisted = 0
            eventDAO.updateEvent(event.value!!)

            //Check that event can be erasable from database
            checkErasable(event)
        }
    }

    /**
     * Checks if event can be erased from database
     * (Favorite, Assisted and Upcoming are equal to 0
     * @param event
     */
    private fun checkErasable(event: LiveData<EventItem>) {
        if (event.value!!.favorite == 0 && event.value!!.assisted == 0 && event.value!!.upcoming == 0)
            eventDAO.deleteEvent(event.value!!)
    }

    /**
     * Gets all events that take place in one location (networking)
     * @param location
     * @return  LiveData<ArrayList<EventItem>>
     */
    fun find(location: String): LiveData<List<EventItem>> {
        var v = MutableLiveData<List<EventItem>>()
        v.value = songKickAPI.find(location)
        return v
    }

    /**
     * Gets all events by name and location (networking)
     * @param location
     * @param name
     * @return LiveData<ArrayList<EventItem>>
     */
    fun find(location: String, name: String): LiveData<List<EventItem>> {
        var v = MutableLiveData<List<EventItem>>()
        v.value =  songKickAPI.find(location, name)
        return v

    }
}