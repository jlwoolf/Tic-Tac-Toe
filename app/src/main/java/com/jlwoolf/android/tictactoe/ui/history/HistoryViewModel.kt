package com.jlwoolf.android.tictactoe.ui.history

import androidx.lifecycle.ViewModel
import com.jlwoolf.android.tictactoe.data.HistoryRepository

//stores the data from the history repository so data
//does not shift on orientation change
class HistoryViewModel(private val historyRepository: HistoryRepository) : ViewModel() {
    val historyItemsLiveData = historyRepository.getHistoryItems()
}