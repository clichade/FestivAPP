package com.example.trjano.festivapp.ui.detail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log

import com.example.trjano.festivapp.data.EventRepository
import com.example.trjano.festivapp.data.database.EventItem

/**
 * Architecture Pattern: ViewModel for EventActivity
 * @see https://developer.android.com/topic/libraries/architecture/viewmodel
 */
class EventActivityViewModel(application: Application) : AndroidViewModel(application) {

    /**Instance to repository which have all necessary actions for EventItem*/
    private val mRepository: EventRepository = EventRepository.getInstance(this.getApplication<Application>().applicationContext)

    lateinit var eventItem: EventItem
    /**The EventItem we want to operate*/
    var liveDataEventItem : LiveData<EventItem?> = mRepository.getLiveDataEvent(id)


    /**
     * Sets the liveDataEventItem to the ViewModel
     * @param event
     */
    fun setValue(event: EventItem){
            this.eventItem = event
    }

    fun eventSetUp(){


    }

    /**
     * Inserts the event as favorite into database if doesn't exists.
     * If exists, it changes to 0.
     * If the event is not marked in any category, the event is deleted
     */
    fun updateFavorite() {
        eventSetUp()
        mRepository.updateFavorite(liveDataEventItem)
    }

    /**
     * Inserts the event as upcoming into database if doesn't exists.
     * If exists, it changes to 0.
     * If the event is not marked in any category, the event is deleted
     */
    fun updateUpcoming() {
        eventSetUp()
        mRepository.updateUpcoming(liveDataEventItem)
    }

    /**
     * Inserts the event as assisted into database if doesn't exists.
     * If exists, it changes to 0.
     * If the event is not marked in any category, the event is deleted
     */
    fun updateAssisted() {
        eventSetUp()

        mRepository.updateAssisted(liveDataEventItem)
    }

    /**
     * Checks if event is marked as favorite
     * @return boolean
     */
    fun isFavorite(): Boolean {
        if (liveDataEventItem.value == null){
            Log.d("eventos","EVENT ITEM ES NULL")
        }

        return mRepository.isFavorite(liveDataEventItem.value!!)
    }




    /**
     * Checks if event is marked as upcoming
     * @return boolean
     */
    fun isUpcoming(): Boolean {
        return mRepository.isUpcoming(liveDataEventItem.value!!)
    }

    /**
     * Checks if event is marked as assisted
     * @return boolean
     */
    fun isAssisted(): Boolean {
        return mRepository.isAssisted(liveDataEventItem.value!!)
    }

}
