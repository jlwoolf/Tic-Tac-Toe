package com.jlwoolf.android.tictactoe.ui.history

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jlwoolf.android.tictactoe.data.HistoryRepository

//handles the creation of a historyViewModel instance with a history repository
class HistoryViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HistoryRepository::class.java)
                .newInstance(HistoryRepository.getInstance(context))
    }
}