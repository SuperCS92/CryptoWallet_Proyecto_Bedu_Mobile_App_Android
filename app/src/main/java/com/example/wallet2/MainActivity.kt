package com.example.wallet2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginFragment = LoginFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,loginFragment)
            .commit()

    }
}