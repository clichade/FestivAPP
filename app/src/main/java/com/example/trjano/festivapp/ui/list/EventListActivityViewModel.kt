package com.example.trjano.festivapp.ui.list

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.trjano.festivapp.data.EventRepository
import com.example.trjano.festivapp.data.database.EventItem
import com.example.trjano.festivapp.data.network.SongKickAPI
import com.example.trjano.festivapp.utilities.Util
import java.util.ArrayList

/**
 * Architecture Pattern: ViewModel for EventListActivity
 * @see https://developer.android.com/topic/libraries/architecture/viewmodel
 * This viewModel can ask repository to get all marked events and the networking API
 */
class EventListActivityViewModel(application: Application) : AndroidViewModel(application) {

    /**Instance to repository which have all necessary actions for EventItem*/
    private val mRepository: EventRepository = EventRepository.getInstance(this.getApplication<Application>().applicationContext)

    /**The List of EventItem we want to operate*/
     var eventList = MutableLiveData<List<EventItem>>()

    /**
     * Sets the eventList to the ViewModel
     * @param list
     */
    fun setValue(list: List<EventItem>?) {
        eventList.value = list
    }

    /**
     * Gets all past events from database source, calling repository
     * @return list events
     */
    fun getAllPastEvents(): List<EventItem> {
        return mRepository.getAllPastEvents()
    }

    /**
     * Gets all upcoming events from database source, calling repository
     * @return list events
     */
    fun getAllUpcomingEvents(): List<EventItem> {
        return mRepository.getAllUpcomingEvents()
    }

    /**
     * Gets all favorite events from database source, calling repository
     * @return list events
     */
    fun getAllFavoriteEvents(): List<EventItem> {
        return mRepository.getAllFavorites()
    }

    /**
     * Gets all events that take place in one location (networking)
     * @param location
     * @return  LiveData<ArrayList<EventItem>>
     */
    fun find(location: String): List<EventItem> {
        return mRepository.find(location)
    }

    /**
     * Gets all events by name and location (networking)
     * @param location
     * @param name
     * @return LiveData<ArrayList<EventItem>>
     */
    fun find(location: String, name: String): List<EventItem> {
        return mRepository.find(location, name)
    }

    /**
     * CALLS THE RESPOSITORY METHOD THAT DOES THE SAME
     * Searches events in the SongKick API and returns them as an ArrayList of EventItem
     * for the non obligatory the default value is "none"
     *
     * @param location_name* Name of the Province we want to search OBLIGATORY
     * @param event_name fragment of the total name of the event we want to search OPTIONAL
     * @param date_start minimum date to start searching the events OPTIONAL
     * @param date_end maximum date to start searching the events OPTIONAL
     * @param type* type of event it can be CONCERT, FESTIVAL or both, default as both CONCERT|FESTIVAL
     *
     * @return the list of event as ArrayList<EventItem>
     */
    fun find(location_name: String, event_name: String = Util.NONE,
             date_start: String = Util.NONE, date_end : String = Util.NONE, type: String = Util.TYPE_BOTH): ArrayList<EventItem> {
        return mRepository.find(location_name,event_name,date_start,date_end,type)
    }

}