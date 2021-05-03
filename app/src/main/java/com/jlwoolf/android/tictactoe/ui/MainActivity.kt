package com.jlwoolf.android.tictactoe.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import com.jlwoolf.android.tictactoe.R
import com.jlwoolf.android.tictactoe.databinding.ActivityMainBinding
import com.jlwoolf.android.tictactoe.ui.settings.SettingsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val LOG_TAG = "TTT.MainActivity"
    }

    //does almost everything that would be expected from a main activity
    //such as initializing/inflating the binding and setting up the navigation host fragment
    //but also access the shared preferences to set the theme upon creation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate() called")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById((binding as ActivityMainBinding).navHostFragment.id) as NavHostFragment
        NavigationUI.setupActionBarWithNavController(this, navHostFragment.navController)

        PreferenceManager.setDefaultValues(this, "PREFS_NAME", Context.MODE_PRIVATE, R.xml.settings, false)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.getString("theme", "system_default")?.let { SettingsFragment.changeTheme(it) }
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
        Log.d(LOG_TAG, "onDestroy() called")
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController((binding as ActivityMainBinding).navHostFragment.id).navigateUp()
                || super.onSupportNavigateUp()
    }
}