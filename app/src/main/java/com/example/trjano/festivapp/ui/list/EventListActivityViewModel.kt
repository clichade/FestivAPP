package com.example.trjano.festivapp.ui.list

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.trjano.festivapp.data.EventRepository
import com.example.trjano.festivapp.data.database.EventItem

/**
 * Architecture Pattern: ViewModel for EventListActivity
 * @see https://developer.android.com/topic/libraries/architecture/viewmodel
 * This viewModel can ask repository to get all marked events and the networking API
 */
class EventListActivityViewModel(application: Application) : AndroidViewModel(application) {

    /**Instance to repository which have all necessary actions for EventItem*/
    private val mRepository: EventRepository = EventRepository.getInstance(this.getApplication<Application>().applicationContext)

    /**The List of EventItem we want to operate*/
     var eventList = MutableLiveData<ArrayList<EventItem>>()

    /**
     * Sets the eventList to the ViewModel
     * @param list
     */
    fun setValue(list: ArrayList<EventItem>) {
        eventList.value = list
    }

    /**
     * Gets all past events from database source, calling repository
     * @return list events
     */
    fun getAllPastEvents(): MutableLiveData<ArrayList<EventItem>> {
        return mRepository.getAllPastEvents()
    }

    /**
     * Gets all upcoming events from database source, calling repository
     * @return list events
     */
    fun getAllUpcomingEvents(): MutableLiveData<ArrayList<EventItem>> {
        return mRepository.getAllUpcomingEvents()
    }

    /**
     * Gets all favorite events from database source, calling repository
     * @return list events
     */
    fun getAllFavoriteEvents(): MutableLiveData<ArrayList<EventItem>> {
        return mRepository.getAllFavorites()
    }

    /**
     * Gets all events that take place in one location (networking)
     * @param location
     * @return  LiveData<ArrayList<EventItem>>
     */
    fun find(location: String): LiveData<ArrayList<EventItem>> {
        return mRepository.find(location)
    }

    /**
     * Gets all events by name and location (networking)
     * @param location
     * @param name
     * @return LiveData<ArrayList<EventItem>>
     */
    fun find(location: String, name: String): LiveData<ArrayList<EventItem>> {
        return mRepository.find(location, name)
    }

}