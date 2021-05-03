package com.jlwoolf.android.tictactoe.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jlwoolf.android.tictactoe.data.HistoryItem
import com.jlwoolf.android.tictactoe.databinding.FragmentHistoryItemBinding

//the adapter used by the recyclerview to load the
//history database data
class HistoryAdapter(private val historyItems: List<HistoryItem>) : RecyclerView.Adapter<HistoryHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val binding = FragmentHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        val historyItem = historyItems[position]
        holder.bind(historyItem)
    }

    override fun getItemCount(): Int {
        return historyItems.size
    }

}