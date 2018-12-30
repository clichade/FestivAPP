package com.example.trjano.festivapp.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.util.Log
import com.example.trjano.festivapp.data.database.AppDatabase
import com.example.trjano.festivapp.data.database.EventDAO
import com.example.trjano.festivapp.data.database.EventItem
import com.example.trjano.festivapp.data.network.SongKickAPI
import com.example.trjano.festivapp.utilities.Util
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
    fun getAllFavorites(): List<EventItem> = eventDAO.getAllFavorites()

    /**
     * Gets all past events from database source
     * @return list events
     */
    fun getAllPastEvents(): List<EventItem> = eventDAO.getAllPastEvents()

    /**
     * Gets all upcoming events from database source
     * @return list events
     */
    fun getAllUpcomingEvents(): List<EventItem> = eventDAO.getAllUpcomingEvents()

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

    fun isFavorite(event: EventItem): Boolean {
        val e: EventItem? = eventDAO.getEvent(event.songkickID)
        return if(e == null) {false}

        else e.favorite == 1

    }

    fun isUpcoming(event: EventItem): Boolean {
        val e: EventItem? = eventDAO.getEvent(event.songkickID)

        return if(e == null) false
        else e.upcoming == 1
    }

    fun isAssisted(event: EventItem): Boolean {
        val e: EventItem? = eventDAO.getEvent(event.songkickID)

        return if(e == null) false
        else e.assisted == 1
    }

    fun getEvent(id: Long): EventItem? {
        return eventDAO.getEvent(id)
    }



    /**
     * Inserts an favorite event into database if doesn't exists.
     * If exists, it changes to 0.
     * If the event is not marked in any category, the event is deleted
     * @param event
     */
    fun updateFavorite(event: EventItem) {

            //If the event is not favorite, now it is
            if (event.favorite == 0) {
                event.favorite = 1

                //If doesn't exists in database, we insert. If exists, we update
                if (eventDAO.getEvent(event.songkickID) == null)
                    eventDAO.insertEvent(event)
                else
                    eventDAO.updateEvent(event)

                //If the event is favorited, now it's not
            } else {
                event.favorite = 0
                //If doesn't exists in database, we insert. If exists, we update
                if (eventDAO.getEvent(event.songkickID) == null)
                    eventDAO.insertEvent(event)
                else
                    eventDAO.updateEvent(event)

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
    fun updateUpcoming(event: EventItem) {



        //If the event is not upcoming, now it is
        if (event.upcoming == 0) {
            event.upcoming = 1


            //If doesn't exists in database, we insert. If exists, we update
            if (eventDAO.getEvent(event.songkickID) == null) {
                eventDAO.insertEvent(event)
            }
            else {
                eventDAO.updateEvent(event)
            }

            //If the event is upcoming, now it's not
        } else {
            event.upcoming = 0
            eventDAO.updateEvent(event)
            //Check that event can be erasable from database
            checkErasable(event)
        }
        Log.d("eventos","POST F: ${event.upcoming} A: ${event.assisted} U: ${event.upcoming}")
    }

    /**
     * Inserts an assisted event into database if doesn't exists.
     * If exists, it changes to 0.
     * If the event is not marked in any category, the event is deleted
     * @param event
     */
    fun updateAssisted(event: EventItem) {

        //If the event is not assisted, now it is
        if (event.assisted == 0) {
            event.assisted = 1

            //If doesn't exists in database, we insert. If exists, we update
            if (eventDAO.getEvent(event.songkickID) == null)
                eventDAO.insertEvent(event)
            else//If the event does exist
                eventDAO.updateEvent(event)
            //If the event is assisted, now it's not
        }
        else {//If the event was assisted
            event.assisted = 0
            eventDAO.updateEvent(event)
            //Check that event can be erasable from database and if it is erase it
            checkErasable(event)
        }
    }

    /**
     * Checks if event can be erased from database
     * (Favorite, Assisted and Upcoming are equal to 0
     * @param event
     */
    private fun checkErasable(event: EventItem) {
        if (event.favorite == 0 && event.assisted == 0 && event.upcoming == 0)
            eventDAO.deleteEvent(event)
    }


    fun find(location_name: String, event_name: String = Util.NONE,
             date_start: String = Util.NONE, date_end : String = Util.NONE, type: String = Util.TYPE_BOTH): ArrayList<EventItem>{
        return SongKickAPI.find(location_name,event_name,date_start,date_end,type)
    }

    /**
     * Gets all events that take place in one location (networking)
     * @param location
     * @return  LiveData<ArrayList<EventItem>>
     */
    fun find(location: String): List<EventItem> = songKickAPI.find(location)

    /**
     * Obtains the URI given the proper event Songkick id
     */
    fun getSonkickUri(id: Long) = songKickAPI.getSongKickUri(id)

    /**
     * Given the Songkick ID of an event returns a Pair containing the Latitude and Longitude of the event location
     * @return Pair(Latitude: Long, Longitude: Long)
     */
    fun getEventCoordinates(id: Long) = songKickAPI.getEventCoordinates(id)



    /**
     * Gets all events by name and location (networking)
     * @param location
     * @param name
     * @return LiveData<ArrayList<EventItem>>
     */
    fun find(location: String, name: String): List<EventItem> = songKickAPI.find(location, name)

}