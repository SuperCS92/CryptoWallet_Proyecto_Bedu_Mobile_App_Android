package com.example.bedu_proyecto_wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

const val SEED_PHRASE = "com.example.bedu_proyecto_wallet.SEED_PHRASE"

class MainActivity : AppCompatActivity() {
    private lateinit var seed: EditText
    private lateinit var btn_login: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        seed = findViewById(R.id.editTextTextPersonName)
        btn_login = findViewById(R.id.button)

        btn_login.setOnClickListener {
            val bundle= Bundle()
            bundle.putString(SEED_PHRASE, seed.text.toString())

            val intent = Intent(this, Dashboard::class.java).apply {
                putExtras(bundle)
            }

            startActivity(intent)
        }

    }
}