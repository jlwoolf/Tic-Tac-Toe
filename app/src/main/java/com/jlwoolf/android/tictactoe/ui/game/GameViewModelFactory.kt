package com.jlwoolf.android.tictactoe.ui.game

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jlwoolf.android.tictactoe.data.Game
import com.jlwoolf.android.tictactoe.data.HistoryRepository

//used to create a view model instance that has both a game
//with the correct game settings and access to the history repository
//for adding items to the history
class GameViewModelFactory(private val computerPlayer: Boolean,
                           private val computerFirst: Boolean,
                           private val computerDifficulty: Int,
                           private val currentPiece: Char,
                           private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Game::class.java, HistoryRepository::class.java).newInstance(
                Game(computerPlayer, computerFirst, computerDifficulty, currentPiece),
                HistoryRepository.getInstance(context)
        )
    }
}