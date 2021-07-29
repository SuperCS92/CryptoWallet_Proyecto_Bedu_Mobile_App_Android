package com.example.bedu_proyecto_wallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

class ReceiveActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    val assets = listOf("BTC", "ETH", "BNB") //List{"BTC", "ETH", "BNB"}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive)
        spinner = findViewById(R.id.spinner_asset_receive)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //Toast.makeText(applicationContext ,assets[position] , Toast.LENGTH_LONG).show()
            }

        }

        val adapter = SpinnerAdapter(this, assets)
        adapter.setDropDownViewResource(R.layout.asset_item_short)
        spinner.adapter = adapter
    }

    //generamos datos dummy con este método
    private fun getAssets_Short(): MutableList<Asset_Short>{
        var assets:MutableList<Asset_Short> = ArrayList()

        assets.add(Asset_Short("BTC",R.drawable.btc))
        assets.add(Asset_Short("ETH", R.drawable.eth))
        assets.add(Asset_Short("BNB",R.drawable.bnb))

        return assets
    }
}