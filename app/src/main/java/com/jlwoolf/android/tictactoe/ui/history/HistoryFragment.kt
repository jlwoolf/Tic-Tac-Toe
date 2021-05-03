package com.jlwoolf.android.tictactoe.ui.history

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jlwoolf.android.tictactoe.R
import com.jlwoolf.android.tictactoe.data.HistoryItem
import com.jlwoolf.android.tictactoe.data.Outcome
import com.jlwoolf.android.tictactoe.databinding.FragmentHistoryBinding
import kotlin.system.exitProcess

//fragment for the history data from the history database
class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private var numWins = 0
    private var numLosses = 0
    private var numDraws = 0

    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var adapter: HistoryAdapter

    companion object {
        private const val LOG_TAG = "TTT.HistoryFragment"
    }

    //updates the UI to have the data loaded from the adapter
    //also counts the number of wins, losses, and draws for the score
    private fun updateUI(historyItems: List<HistoryItem>) {
        numWins = historyItems.count() {it.outcome == Outcome.WIN}
        numLosses = historyItems.count() {it.outcome == Outcome.LOSS}
        numDraws = historyItems.count() {it.outcome == Outcome.DRAW}

        adapter = HistoryAdapter(historyItems)
        binding.historyListRecycleView.adapter = adapter
        binding.scoreTextView.text = resources.getString(R.string.score,
                numWins, numLosses, numDraws)
    }

    //initializes the view model that handles the interactions
    //between the fragment and the history repository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate() called")

        val factory = HistoryViewModelFactory(requireContext())
        historyViewModel = ViewModelProvider(this@HistoryFragment, factory).get(HistoryViewModel::class.java)

        setHasOptionsMenu(true)
    }

    //initializes the binding and defines the recycler view to be a linear layout
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.d(LOG_TAG, "onCreateView() called")
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        binding.historyListRecycleView.layoutManager = LinearLayoutManager(context)

        updateUI(emptyList())
        return binding.root
    }

    //loads the options menu for the history fragment
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        Log.d(LOG_TAG, "onCreateOptionsMenu() called")
        inflater.inflate(R.menu.fragment_history_menu, menu)
    }

    //populates the recycler view with the history data
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyViewModel.historyItemsLiveData.observe(
                viewLifecycleOwner,
                Observer { historyItems ->
                    historyItems?.let {
                        Log.i(LOG_TAG, "Got history ${historyItems.size}")
                        updateUI(historyItems)
                    }
                })
    }

    override fun onStart() {
        super.onStart()
        Log.d(LOG_TAG, "onStart() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(LOG_TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(LOG_TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "onDestory() called")
    }

    //handles the navigation when menu items selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(LOG_TAG, "onOptionsItemSelected() called")
        return when(item.itemId) {
            R.id.play_game_menu_item -> {
                val action = HistoryFragmentDirections.actionHistoryFragmentToGameFragment()
                findNavController().navigate(action)
                true
            } R.id.settings_menu_item -> {
                val action = HistoryFragmentDirections.actionHistoryFragmentToSettingsFragment()
                findNavController().navigate(action)
                true
            } R.id.exit_menu_item -> {
                exitProcess(0)
            } else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}