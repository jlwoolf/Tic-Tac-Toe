package com.jlwoolf.android.tictactoe

import android.app.Application
import android.util.Log

class TTTApplication : Application() {
    companion object {
        private const val LOG_TAG = "TTT.TTTApplication"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG, "onCreate() called")
    }
}