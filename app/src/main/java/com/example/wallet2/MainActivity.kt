package com.example.wallet2

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)


    }

    private fun goToLogin() {
        val loginFragment = LoginFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, loginFragment)
            .commit()
    }

    private fun goToDashboard() {
        val dashboardFragment = DashboardFragment()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, dashboardFragment)
            .commit()
    }


    override fun onStart() {
        super.onStart()
        if(isLogged()){
            goToDashboard()
        }
        else{
            goToLogin()
        }

    }

    private fun isLogged(): Boolean {
        return preferences.getBoolean(IS_LOGGED, false)
    }
}