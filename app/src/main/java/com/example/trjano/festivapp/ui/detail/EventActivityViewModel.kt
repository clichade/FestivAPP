package com.example.trjano.festivapp.ui.detail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData

import com.example.trjano.festivapp.data.EventRepository
import com.example.trjano.festivapp.data.database.EventItem

/**
 * Architecture Pattern: ViewModel for EventActivity
 * @see https://developer.android.com/topic/libraries/architecture/viewmodel
 */
class EventActivityViewModel(application: Application) : AndroidViewModel(application) {

    /**Instance to repository which have all necessary actions for EventItem*/
    private val mRepository: EventRepository = EventRepository.getInstance(this.getApplication<Application>().applicationContext)

    /**The EventItem we want to operate*/
    val eventItem = MutableLiveData<EventItem>()


    /**
     * Sets the eventItem to the ViewModel
     * @param event
     */
    fun setValue(event: EventItem){
        eventItem.value = event
    }

    /**
     * Inserts the event as favorite into database if doesn't exists.
     * If exists, it changes to 0.
     * If the event is not marked in any category, the event is deleted
     */
    fun updateFavorite() {
        mRepository.updateFavorite(eventItem)
    }

    /**
     * Inserts the event as upcoming into database if doesn't exists.
     * If exists, it changes to 0.
     * If the event is not marked in any category, the event is deleted
     */
    fun updateUpcoming() {
        mRepository.updateUpcoming(eventItem)
    }

    /**
     * Inserts the event as assisted into database if doesn't exists.
     * If exists, it changes to 0.
     * If the event is not marked in any category, the event is deleted
     */
    fun updateAssisted() {
        mRepository.updateAssisted(eventItem)
    }

    /**
     * Checks if event is marked as favorite
     * @return boolean
     */
    fun isFavorite(): Boolean {
        return eventItem.value!!.favorite == 1
    }

    /**
     * Checks if event is marked as upcoming
     * @return boolean
     */
    fun isUpcoming(): Boolean {
        return eventItem.value!!.upcoming == 1
    }

    /**
     * Checks if event is marked as assisted
     * @return boolean
     */
    fun isAssisted(): Boolean {
        return eventItem.value!!.assisted == 1
    }

}
