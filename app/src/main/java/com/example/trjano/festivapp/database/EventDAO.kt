package com.example.trjano.festivapp.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface EventDAO {


    /**********************
     * getAllEvents methods
     **********************/
    @Query("SELECT * FROM "+DBContract.EventItem.TABLE_NAME+" WHERE "+DBContract.EventItem.FAVORITE+"=1")
    fun getAllFavorites(): List<EventItem>


    @Query("SELECT * FROM "+DBContract.EventItem.TABLE_NAME+ " WHERE "+DBContract.EventItem.ASSISTED+"=1")
    fun getAllPastEvents(): List<EventItem>


    @Query("SELECT * FROM "+DBContract.EventItem.TABLE_NAME+" WHERE "+DBContract.EventItem.UPCOMING+"=1")
    fun getAllUpcomingEvents(): List<EventItem>


    /*******************
     * getEvents methods
     *******************/

    @Query("SELECT * FROM "+DBContract.EventItem.TABLE_NAME+" WHERE "+DBContract.EventItem._ID+" = :id" +
            " AND "+DBContract.EventItem.FAVORITE+"=1")
    fun getFavorite(id: Long): EventItem

    @Query("SELECT * FROM "+DBContract.EventItem.TABLE_NAME+" WHERE "+DBContract.EventItem._ID+" = :id " +
            "AND "+DBContract.EventItem.ASSISTED+"=1")
    fun getPastEvent(id: Long): EventItem

    @Query("SELECT * FROM "+DBContract.EventItem.TABLE_NAME+" WHERE "+DBContract.EventItem._ID+" = :id " +
            "AND "+DBContract.EventItem.UPCOMING+"=1")
    fun getUpcomingEvent(id: Long): EventItem

    /********************
     * deleteAll methods
     ********************/

    @Query("DELETE FROM "+DBContract.EventItem.TABLE_NAME+" WHERE "+DBContract.EventItem.ASSISTED+"=1")
    fun deleteAllPastEvents()

    @Query("DELETE FROM "+DBContract.EventItem.TABLE_NAME+" WHERE "+DBContract.EventItem.UPCOMING+"=1")
    fun deleteAllUpcomingEvents()

    @Query("DELETE FROM "+DBContract.EventItem.TABLE_NAME+" WHERE "+DBContract.EventItem.FAVORITE+"=1")
    fun deleteAllFavorites()


    /*******************
     * insert and delete
     *******************/
    @Insert
    fun insertEvent(item: EventItem): Long

    @Delete
    fun deleteEvent(item: EventItem)

}