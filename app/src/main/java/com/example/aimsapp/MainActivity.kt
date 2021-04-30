package com.example.aimsapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.aimsapp.views.currentTrip.CurrentTripFragment
import com.example.aimsapp.views.currentTrip.CurrentTripFragmentDirections
import com.example.aimsapp.views.map.MapFragment
import com.example.aimsapp.views.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
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
}