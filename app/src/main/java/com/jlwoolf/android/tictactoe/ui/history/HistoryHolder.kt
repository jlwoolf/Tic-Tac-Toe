package com.jlwoolf.android.tictactoe.ui.history

import androidx.recyclerview.widget.RecyclerView
import com.jlwoolf.android.tictactoe.data.HistoryItem
import com.jlwoolf.android.tictactoe.data.Outcome
import com.jlwoolf.android.tictactoe.databinding.FragmentHistoryItemBinding

//used by the adapter to create the history data fragment items
//used in the recycler view of the history fragment
class HistoryHolder(private val binding: FragmentHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            private lateinit var historyItem: HistoryItem

            fun bind(historyItem: HistoryItem) {
                this.historyItem = historyItem
                if(this.historyItem.outcome == Outcome.DRAW) {
                    binding.winnerTextView.text = "Draw"
                    binding.loserTextView.text = "Draw"
                }
                binding.winningPlayer.text = this.historyItem.winner
                binding.winningPlayerPiece.text = this.historyItem.winnerPiece.toString()
                binding.losingPlayer.text = this.historyItem.loser
                binding.losingPlayerPiece.text = this.historyItem.loserPiece.toString()
                binding.dateTextView.text = this.historyItem.date.toString()
            }
}