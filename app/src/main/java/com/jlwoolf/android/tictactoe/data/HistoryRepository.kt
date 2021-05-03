package com.jlwoolf.android.tictactoe.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import java.util.concurrent.Executors

//the history repository that serves as the connection between the fragments
//and the history database
class HistoryRepository private constructor(private val historyDao: HistoryDao){
    private val executor = Executors.newSingleThreadExecutor()
    companion object {
        private var INSTANCE: HistoryRepository? = null
        fun getInstance(context: Context): HistoryRepository {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    val database = HistoryDatabase.getInstance(context)
                    instance = HistoryRepository(database.historyDao)
                    INSTANCE = instance
                }
                return instance
            }
        }
        private const val LOG_TAG = "TTT.HistoryRepository"
    }

    //two main functions that are used to read and write data
    //to the history database
    fun getHistoryItems(): LiveData<List<HistoryItem>> = historyDao.getHistoryItems()
    fun addHistoryItem(HistoryItem: HistoryItem) {
        executor.execute {
            Log.d(LOG_TAG, "addHistoryItem() called")
            historyDao.addHistoryItem(HistoryItem)
        }
    }
    //helper function to clear the database
    fun clearDatabase() {
        executor.execute {
            Log.d(LOG_TAG, "clearDatabase() called")
            historyDao.clearHistoryItems()
        }
    }
}