package com.jlwoolf.android.tictactoe.ui.welcome

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jlwoolf.android.tictactoe.R
import com.jlwoolf.android.tictactoe.databinding.FragmentWelcomeBinding
import kotlin.system.exitProcess

class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var welcomeViewModel: WelcomeViewModel

    companion object {
        private const val LOG_TAG = "TTT.WelcomeFragment"
    }

    //initializes the welcomeViewModel with a new instance from the factory
    //it does not need this and I could remove it, but I'll keep it anyway for now
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate() called")

        val factory = WelcomeViewModelFactory()
        welcomeViewModel = ViewModelProvider(this@WelcomeFragment, factory).get(WelcomeViewModel::class.java)
        setHasOptionsMenu(true)
    }

    //initializes the view binding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(LOG_TAG, "onCreateView() called")
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        Log.d(LOG_TAG, "onCreateOptionsMenu() called")
        inflater.inflate(R.menu.fragment_welcome_menu, menu)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(LOG_TAG, "onAttach() called")
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

    //handles the transition between the welcome menu and the other
    //fragments
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(LOG_TAG, "onOptionsItemSelected() called")
        return when(item.itemId) {
            R.id.play_game_menu_item -> {
                val action = WelcomeFragmentDirections.actionWelcomeFragmentToGameFragment()
                findNavController().navigate(action)
                true
            } R.id.history_menu_item -> {
                val action = WelcomeFragmentDirections.actionWelcomeFragmentToHistoryFragment()
                findNavController().navigate(action)
                true
            } R.id.settings_menu_item -> {
                val action = WelcomeFragmentDirections.actionWelcomeFragmentToSettingsFragment()
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