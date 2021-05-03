package com.jlwoolf.android.tictactoe.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//item that is used by the history database to store game data
@Entity
class HistoryItem(@PrimaryKey val id: UUID = UUID.randomUUID(),
                  var outcome: Outcome = Outcome.WIN,
                  var winner: String = "",
                  var loser: String = "",
                  var winnerPiece: Char = ' ',
                  var loserPiece: Char = ' ',
                  var date: Date = Date()) {
}