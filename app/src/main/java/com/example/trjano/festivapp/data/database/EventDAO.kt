package com.example.trjano.festivapp.data.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface EventDAO {


    /**********************
     * getAllEvents methods
     **********************/
    @Query("SELECT * FROM "+ DBContract.EventItem.TABLE_NAME +" WHERE "+ DBContract.EventItem.FAVORITE +"=1")
    fun getAllFavorites(): LiveData<List<EventItem>>


    @Query("SELECT * FROM "+ DBContract.EventItem.TABLE_NAME + " WHERE "+ DBContract.EventItem.ASSISTED +"=1")
    fun getAllPastEvents(): LiveData<List<EventItem>>


    @Query("SELECT * FROM "+ DBContract.EventItem.TABLE_NAME +" WHERE "+ DBContract.EventItem.UPCOMING +"=1")
    fun getAllUpcomingEvents(): LiveData<List<EventItem>>


    /**
     * Todo Añadir la funcionalidad Update
       EventDao: Añadir un método @Update del evento, que será llamado desde Repository para realizar lo anteriormente mencionado
     */

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
     * Get, insert and delete
     ************************/
    @Insert
    fun insertEvent(item: LiveData<EventItem>): Long

    @Delete
    fun deleteEvent(item: LiveData<EventItem>)

    @Query("SELECT * FROM "+ DBContract.EventItem.TABLE_NAME +" WHERE "+ DBContract.EventItem._ID+" = :id")
    fun getEvent(id: Long): LiveData<EventItem>

}