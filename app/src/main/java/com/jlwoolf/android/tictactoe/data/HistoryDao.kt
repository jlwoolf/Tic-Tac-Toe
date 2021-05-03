package com.jlwoolf.android.tictactoe.data

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

//Dao used for interacting with the database
@Dao
interface HistoryDao {
    @Query("SELECT * FROM historyitem")
    fun getHistoryItems(): LiveData<List<HistoryItem>>

    @Query("SELECT * FROM historyitem WHERE id=(:id)")
    fun getHistoryItem(id: UUID): LiveData<HistoryItem?>

    @Update
    fun updateHistoryItem(historyItem: HistoryItem)

    @Insert
    fun addHistoryItem(historyItem: HistoryItem)

    @Delete
    fun removeHistoryItem(historyItem: HistoryItem)

    @Query("DELETE FROM historyitem")
    fun clearHistoryItems()
}