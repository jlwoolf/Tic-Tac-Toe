package com.jlwoolf.android.tictactoe.ui.settings

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.jlwoolf.android.tictactoe.R

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var numPlayersPreference: ListPreference
    private lateinit var computerDifficultyPreference: ListPreference
    private lateinit var computerFirstPreference: SwitchPreference
    private lateinit var whoPlaysFirstPreference: ListPreference
    private lateinit var themePreference: ListPreference


    companion object {
        private const val LOG_TAG = "TTT.SettingsFragment"
        //fun used to change the theme of the app. Uses androids built in dark and light mode
        //can be called from anywhere, and is called by the main activity upon creation
        fun changeTheme(newValue: Any): Boolean {
            Log.d(LOG_TAG, "changeTheme() called")
            when (newValue) {
                "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            return true
        }
    }

    //updates the ui so that the computer options are disabled
    //when the app is in two player mode
    private fun updateUI(newValue: Any): Boolean {
        Log.d(LOG_TAG, "updateUI() called")
        when(newValue) {
            "two_players" -> {
                Log.d(LOG_TAG, "two_players selected")
                computerDifficultyPreference.shouldDisableView = true
                computerDifficultyPreference.isEnabled = false
                computerFirstPreference.shouldDisableView = true
                computerFirstPreference.isEnabled = false
            }
            "one_player" -> {
                Log.d(LOG_TAG, "one_player selected")
                computerDifficultyPreference.shouldDisableView = false
                computerDifficultyPreference.isEnabled = true
                computerFirstPreference.shouldDisableView = false
                computerFirstPreference.isEnabled = true
            }
        }
        return true
    }

    //initializes the preferences with there respective values
    //the reason this was done was so that a preference change listener could
    //be attached to the num players preference and the theme preference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate() called")

        numPlayersPreference = preferenceManager.findPreference<ListPreference>("num_players")!!
        computerDifficultyPreference = preferenceManager.findPreference<ListPreference>("computer_difficulty")!!
        computerFirstPreference = preferenceManager.findPreference<SwitchPreference>("computer_first")!!
        whoPlaysFirstPreference = preferenceManager.findPreference<ListPreference>("who_plays_first")!!
        themePreference = preferenceManager.findPreference<ListPreference>("theme")!!

        numPlayersPreference.setOnPreferenceChangeListener {_, newValue ->
            updateUI(newValue)
        }
        themePreference.setOnPreferenceChangeListener { _, newValue ->
            changeTheme(newValue)
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        Log.d(LOG_TAG, "onCreatePreferences() called")
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onStart() {
        super.onStart()
        Log.d(LOG_TAG, "onStart() called")
        updateUI(numPlayersPreference.value)
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
        Log.d(LOG_TAG, "onDestroy() called")
    }
}