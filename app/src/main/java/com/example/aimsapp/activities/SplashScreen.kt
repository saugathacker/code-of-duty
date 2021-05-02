package com.example.aimsapp.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.aimsapp.R


class SplashScreen: AppCompatActivity() {

    private val SPLASH_TIME_OUT = 1550L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

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
            this.startActivity(i)

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }
}