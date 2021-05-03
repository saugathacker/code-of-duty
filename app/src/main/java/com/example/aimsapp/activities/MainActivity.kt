package com.example.aimsapp.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.aimsapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("MainActivity1","Main Started")
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setUpNavigation()
    }

    private fun setUpNavigation(){
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bttm_nav)
        val navController = findNavController(R.id.nav_host_fragment)

        NavigationUI.setupWithNavController(
            bottomNavigationView,
            navController
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        val sharedPreferences = this.getSharedPreferences("login shared prefs", Context.MODE_PRIVATE)

        sharedPreferences.edit().putBoolean("loggedIn", true)
    }
}