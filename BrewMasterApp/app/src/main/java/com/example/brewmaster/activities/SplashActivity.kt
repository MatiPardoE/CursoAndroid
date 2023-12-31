package com.example.brewmaster.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.brewmaster.R

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long = 1000 // 3 sec
    private val PREF_NAME = "myPreferences"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler().postDelayed(

            {
                val sharedPref: SharedPreferences = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
                val str = sharedPref.getString("USER", "")
                if(str == "") {
                    startActivity(Intent(this, SignInActivity::class.java))
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                finish()
            }
            , SPLASH_TIME_OUT)
    }
}