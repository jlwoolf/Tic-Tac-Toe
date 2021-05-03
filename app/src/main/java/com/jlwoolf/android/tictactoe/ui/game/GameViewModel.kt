package com.jlwoolf.android.tictactoe.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jlwoolf.android.tictactoe.data.Game
import com.jlwoolf.android.tictactoe.data.HistoryItem
import com.jlwoolf.android.tictactoe.data.HistoryRepository

//used to store the data so that the board doesn't reset on rotation
class GameViewModel(var game: Game, private val historyRepository: HistoryRepository) : ViewModel() {
    fun addHistoryItem(historyItem: HistoryItem) {
        Log.d(LOG_TAG, "addHistoryItem() called")
        Log.d(LOG_TAG, "date ${historyItem.date}")


        historyRepository.addHistoryItem(historyItem)
    }
    companion object {
        private const val LOG_TAG = "TTT.GameViewModel"
    }
}