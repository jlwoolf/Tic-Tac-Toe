package com.jlwoolf.android.tictactoe.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

//history database class
@Database(entities = [HistoryItem::class], version = 2)
@TypeConverters(HistoryTypeConverters::class)
abstract class HistoryDatabase : RoomDatabase() {
    abstract val historyDao: HistoryDao

    companion object {
        private const val DATABASE_NAME = "history-database"
        private var INSTANCE: HistoryDatabase? = null

        fun getInstance(context: Context): HistoryDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                            HistoryDatabase::class.java,
                            DATABASE_NAME)
                            .build()
                }
                return instance
            }
        }
    }
}