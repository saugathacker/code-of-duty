package com.example.aimsapp.activities

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.aimsapp.R


class SplashScreen: AppCompatActivity() {

    private val SPLASH_TIME_OUT = 1400L
    private var loggedIn = false;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        Log.i("MainActivity1", "started splash")

        val sharedPreferences = this.getSharedPreferences("login shared prefs", Context.MODE_PRIVATE)

        loggedIn = sharedPreferences.getBoolean("loggedIn", false)


        val truck = findViewById<ImageView>(R.id.truck)
        val logo = findViewById<ImageView>(R.id.ilogo)
        val drop = findViewById<ImageView>(R.id.drop)
        val loginLayout = findViewById<FrameLayout>(R.id.loginLayout)
        val dropAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_drop)
        truck.startAnimation(dropAnimation)
        val logoAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_logo)
        logo.startAnimation(logoAnimation)
        val truckAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_truck)
        drop.startAnimation(truckAnimation)
        val loginAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_login)

        Handler().postDelayed(Runnable { // This method will be executed once the timer is over
            // Start your app main activity
            if(loggedIn) {
                val i = Intent(this@SplashScreen, MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                startActivity(i)

                // close this activity
                finish()
            }
            else{
                loginLayout.startAnimation(loginAnimation)
                loginLayout.visibility = View.VISIBLE
            }

        }, SPLASH_TIME_OUT)

    }



}