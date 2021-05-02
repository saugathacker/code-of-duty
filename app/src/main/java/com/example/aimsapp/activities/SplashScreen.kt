package com.example.aimsapp.activities

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.aimsapp.R


class SplashScreen: AppCompatActivity() {

    private val SPLASH_TIME_OUT = 1400L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        Log.i("MainActivity1", "started splash")


        val truck = findViewById<ImageView>(R.id.truck)
        val logo = findViewById<ImageView>(R.id.ilogo)
        val drop = findViewById<ImageView>(R.id.drop)
        val dropAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_drop)
        truck.startAnimation(dropAnimation)
        val logoAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_logo)
        logo.startAnimation(logoAnimation)
        val truckAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_truck)
        drop.startAnimation(truckAnimation)

        Handler().postDelayed(Runnable { // This method will be executed once the timer is over
            // Start your app main activity
            val i = Intent(this@SplashScreen, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT

            val pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE)
            if (pref.getBoolean("activity_executed", false)) {
                Log.i("MainActivity1", "Starting")
                startActivity(i)
                Log.i("MainActivity1", "closing splash")
                finish()
            } else {
                Log.i("MainActivity1", "execited false")
                val ed: SharedPreferences.Editor = pref.edit()
                ed.putBoolean("activity_executed", true)
                ed.commit()
            }

            // close this activity

        }, SPLASH_TIME_OUT)

    }

}