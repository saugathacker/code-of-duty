package com.example.aimsapp.activities

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.aimsapp.R

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("MainActivity1", "Main Started")
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setUpNavigation()

        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
                TODO("Not yet implemented")
            }

        }

        TedPermission.with(this)
            .setPermissionListener(permissionlistener)
            .setRationaleTitle("Permission Required")
            .setRationaleMessage("Please allow permissions for this app to function properly.")
            .setDeniedTitle("Permission denied")
            .setDeniedMessage(
                "If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]"
            )
            .setGotoSettingButtonText("bla bla")
            .setPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.VIBRATE
            )
            .check()




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
        val sharedPreferences = this.getSharedPreferences(
            "login shared prefs",
            Context.MODE_PRIVATE
        )

        sharedPreferences.edit().putBoolean("loggedIn", true)
    }
}