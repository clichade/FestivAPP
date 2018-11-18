package com.example.trjano.festivapp.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.trjano.festivapp.eventhierarchy.FavoriteItem
import com.example.trjano.festivapp.eventhierarchy.PastItem
import com.example.trjano.festivapp.eventhierarchy.UpcomingItem

@Dao
interface EventDAO {

    /**
     * FAVORITES
     */
    @Query("SELECT * FROM "+DBContract.EventItem.TABLE_NAME_FAVORITES)
    fun getAllFavorites(): List<FavoriteItem>

    @Query("SELECT * FROM "+DBContract.EventItem.TABLE_NAME_FAVORITES+" WHERE "+DBContract.EventItem._ID+" = :id")
    fun getFavorite(id: Long): FavoriteItem

    @Insert
    fun insertFavorite(item: FavoriteItem): Long

    @Delete
    fun deleteFavorite(item: FavoriteItem)

    @Query("DELETE FROM "+DBContract.EventItem.TABLE_NAME_FAVORITES)
    fun deleteAllFavorites()


    /**
     * PAST EVENTS
     */
    @Query("SELECT * FROM "+DBContract.EventItem.TABLE_NAME_PAST)
    fun getAllPastEvents(): List<PastItem>

    @Query("SELECT * FROM "+DBContract.EventItem.TABLE_NAME_PAST+" WHERE "+DBContract.EventItem._ID+" = :id")
    fun getPastEvent(id: Long): PastItem

    @Insert
    fun insertPastEvent(item: PastItem): Long

    @Delete
    fun deletePastEvent(item: PastItem)

    @Query("DELETE FROM "+DBContract.EventItem.TABLE_NAME_PAST)
    fun deleteAllPastEvents()


    /**
     * UPCOMING EVENTS
     */

    @Query("SELECT * FROM "+DBContract.EventItem.TABLE_NAME_UPCOMING)
    fun getAllUpcomingEvents(): List<UpcomingItem>

    @Query("SELECT * FROM "+DBContract.EventItem.TABLE_NAME_UPCOMING+" WHERE "+DBContract.EventItem._ID+" = :id")
    fun getUpcomingEvent(id: Long): UpcomingItem

    @Insert
    fun insertUpcomingEvent(item: UpcomingItem): Long

    @Delete
    fun deleteUpcomingEvent(item: UpcomingItem)

    @Query("DELETE FROM "+DBContract.EventItem.TABLE_NAME_UPCOMING)
    fun deleteAllUpcomingEvents()
}