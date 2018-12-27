package com.example.trjano.festivapp.ui.detail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
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


    var liveDataEventItem = MutableLiveData<EventItem>()


    /**
     * Sets the liveDataEventItem to the ViewModel
     * @param event
     */
    fun setValue(event: EventItem) {
        liveDataEventItem.value = event
    }

    fun eventSetUp() {
        if (liveDataEventItem.value == null)
            Log.d("eventos", "el contenido del live data es nulo")

        val event: EventItem? = mRepository.getEvent(liveDataEventItem.value!!.songkickID)
        if (event != null) {
            liveDataEventItem.postValue(event)
            Log.d("eventos", "El evento existia en BD")
        } else Log.d("eventos", "El evento NO existia en BD")

    }

    /**
     * Inserts the event as favorite into database if doesn't exists.
     * If exists, it changes to 0.
     * If the event is not marked in any category, the event is deleted
     */
    fun updateFavorite() {
        mRepository.updateFavorite(liveDataEventItem.value!!)
    }

    /**
     * Inserts the event as upcoming into database if doesn't exists.
     * If exists, it changes to 0.
     * If the event is not marked in any category, the event is deleted
     */
    fun updateUpcoming() {
        mRepository.updateUpcoming(liveDataEventItem.value!!)
    }

    /**
     * Inserts the event as assisted into database if doesn't exists.
     * If exists, it changes to 0.
     * If the event is not marked in any category, the event is deleted
     */
    fun updateAssisted() {
        mRepository.updateAssisted(liveDataEventItem.value!!)
    }

    /**
     * Checks if event is marked as favorite
     * @return boolean
     */
    fun isFavorite(): Boolean {
        Log.d("eventos","PRE F: ${liveDataEventItem.value!!.favorite} A: ${liveDataEventItem.value!!.assisted} U: ${liveDataEventItem.value!!.upcoming}")
        return liveDataEventItem.value!!.favorite == 1
    }

    /**
     * Checks if event is marked as upcoming
     * @return boolean
     */
    fun isUpcoming(): Boolean = liveDataEventItem.value!!.upcoming == 1


    /**
     * Checks if event is marked as assisted
     * @return boolean
     */
    fun isAssisted(): Boolean = liveDataEventItem.value!!.assisted == 1


}
