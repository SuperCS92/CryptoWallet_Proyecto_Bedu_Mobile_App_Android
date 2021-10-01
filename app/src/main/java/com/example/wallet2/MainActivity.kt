package com.example.wallet2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    var handler: Handler? = null
    var r: Runnable? = null

    private lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //goToDashboard()
        preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        handler = Handler()
        r = Runnable {
            Toast.makeText(
                this@MainActivity,
                "Logging out because of inactivity of the user",
                Toast.LENGTH_SHORT
            ).show()
            preferences.edit()
                .putBoolean(IS_LOGGED, false)
                .apply()
            val loginFragment = LoginFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, loginFragment)
                .commit()
        }
        startHandler()


    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        stopHandler() //stop first and then start
        startHandler()
    }

    fun stopHandler() {
        handler!!.removeCallbacks(r!!)
    }

    fun startHandler() {
        // After 20 minutes of inactivity the app is going to close
        handler!!.postDelayed(r!!, (20 * 60 * 1000).toLong()) //for 20 minutes
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

    // Overwrite this method in order to get the context of each fragment (used in QR scanner)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}