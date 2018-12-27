package com.example.trjano.festivapp.data.database

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.*

/**
 * Architecture Component: Room Dao
 * Room provides automatically implemented methods with correct tags.
 * They return LiveData
 * @see https://developer.android.com/topic/libraries/architecture/room
 * It must have annotations DAO. For methods: Query, Insert, Update and Delete
 */
@Dao
interface EventDAO {


    /**********************
     * getAllEvents methods
     **********************/
    @Query("SELECT * FROM "+ DBContract.EventItem.TABLE_NAME +" WHERE "+ DBContract.EventItem.FAVORITE +"=1")
    fun getAllFavorites(): List<EventItem>


    @Query("SELECT * FROM "+ DBContract.EventItem.TABLE_NAME + " WHERE "+ DBContract.EventItem.ASSISTED +"=1")
    fun getAllPastEvents(): List<EventItem>


    @Query("SELECT * FROM "+ DBContract.EventItem.TABLE_NAME +" WHERE "+ DBContract.EventItem.UPCOMING +"=1")
    fun getAllUpcomingEvents(): List<EventItem>

    /********************
     * deleteAll methods
     ********************/

    @Query("DELETE FROM "+ DBContract.EventItem.TABLE_NAME +" WHERE "+ DBContract.EventItem.ASSISTED +"=1")
    fun deleteAllPastEvents()

    @Query("DELETE FROM "+ DBContract.EventItem.TABLE_NAME +" WHERE "+ DBContract.EventItem.UPCOMING +"=1")
    fun deleteAllUpcomingEvents()

    @Query("DELETE FROM "+ DBContract.EventItem.TABLE_NAME +" WHERE "+ DBContract.EventItem.FAVORITE +"=1")
    fun deleteAllFavorites()


    /************************
     *          CRUD        *
     ************************/
    @Insert
    fun insertEvent(item: EventItem): Long

    @Delete
    fun deleteEvent(item: EventItem)

    @Query("SELECT * FROM "+ DBContract.EventItem.TABLE_NAME +" WHERE "+ DBContract.EventItem.SONGKICK_ID+" = :id")
    fun getEvent(id: Long): EventItem?

    @Update
    fun updateEvent(item: EventItem)

}