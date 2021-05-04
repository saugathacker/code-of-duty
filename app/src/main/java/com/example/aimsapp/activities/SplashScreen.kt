package com.example.aimsapp.activities

import android.app.ActivityOptions
import android.app.AlertDialog
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
import androidx.databinding.DataBindingUtil
import com.example.aimsapp.R
import com.example.aimsapp.databinding.SplashScreenBinding


class SplashScreen: AppCompatActivity() {

    private val SPLASH_TIME_OUT = 1400L
    private var loggedIn = false;
    private lateinit var binding: SplashScreenBinding
    private var driverIdList = arrayListOf<String>("D1","D2","D3","CodeOfDuty")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("MainActivity1", "started splash")
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.splash_screen,null,false)
        setContentView(binding.root)


        val sharedPreferences = this.getSharedPreferences("login shared prefs", Context.MODE_PRIVATE)

        loggedIn = sharedPreferences.getBoolean("loggedIn", false)


        val truck = binding.truck
        val logo = binding.ilogo
        val drop = binding.drop
        val loginLayout = binding.loginLayout
        val driverId = binding.driverIdEdit
        val password = binding.passwordEdit
        val loginButton = binding.loginButton

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

        loginButton.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(this)
            if(driverId.text.isNullOrBlank() || password.text.isNullOrBlank()){

                alertDialogBuilder.setTitle("Incorrect Driver Id or Password")
                alertDialogBuilder.setMessage("Please enter correct information")
                alertDialogBuilder.setCancelable(false)
                alertDialogBuilder.setNegativeButton("OK"
                ){ _, _ ->
                }
                val dialog = alertDialogBuilder.create()
                dialog.show()
            }
            else{
                if (driverIdList.contains(driverId.text.toString())){
                    sharedPreferences.edit().putBoolean("loggedIn", true).apply()
                    val i = Intent(this@SplashScreen, MainActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    startActivity(i)

                    // close this activity
                    finish()
                }
                else{
                    alertDialogBuilder.apply {
                        setTitle("Driver Id does not exist!")
                        setMessage("Please enter a valid Driver Id")
                        setCancelable(false)
                        setNegativeButton("OK"){_,_ ->}
                    }
                    val dialog = alertDialogBuilder.create()
                    dialog.show()
                }
            }
        }

    }
}
