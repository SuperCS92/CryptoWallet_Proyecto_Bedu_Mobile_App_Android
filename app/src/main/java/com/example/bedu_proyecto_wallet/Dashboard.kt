package com.example.bedu_proyecto_wallet

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Dashboard : AppCompatActivity() {
    private lateinit var btn_send:Button
    private lateinit var btn_receive:Button
    private lateinit var btn_assets:Button
    private lateinit var btn_activity:Button

    private lateinit var recyclerContacts: RecyclerView
    private lateinit var mAdapter : RecyclerAdapter
    private lateinit var mAdapter_activity: RecyclerAdapter_AssetActivity

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        btn_assets = findViewById(R.id.button3)
        btn_activity = findViewById(R.id.button4)

        btn_assets.setOnClickListener {
            setUpRecyclerView()
            btn_assets.setBackgroundColor(R.color.purple_200)
            btn_assets.setTextColor(R.color.white)

            btn_activity.setBackgroundColor(android.R.color.transparent)
            btn_activity.setTextColor(R.color.black)
        }

        btn_activity.setOnClickListener {
            setUpRecyclerView_Activity()
            btn_activity.setBackgroundColor(R.color.purple_200)
            btn_activity.setTextColor(R.color.white)

            btn_assets.setBackgroundColor(android.R.color.transparent)
            btn_assets.setTextColor(R.color.black)
        }

        recyclerContacts = findViewById(R.id.recyclerContacts)

        btn_assets.isSelected = true
    }

    //generamos datos dummy con este método
    private fun getContacts(): MutableList<Asset>{
        var assets:MutableList<Asset> = ArrayList()

        assets.add(Asset("Bitcoin", 0.5, 38000.0,19000.0,R.drawable.btc_symbol))
        assets.add(Asset("Ether", 3.0, 2000.0,6000.0,R.drawable.ethereum_symbol))
        assets.add(Asset("Cardano", 500.0, 1.33,665.0,R.drawable.cardano_symbol))
        assets.add(Asset("Tether", 50.0, 1.0,50.0,R.drawable.tether_symbol))
        assets.add(Asset("Binance", 7.0, 300.0,2100.0,R.drawable.bnb_symbol))
        assets.add(Asset("xDai", 17.0, 1.0,17.0,R.drawable.xdai_symbol))
        assets.add(Asset("ChainLink", 35.0, 18.35,642.25,R.drawable.chainlink_symbol))
        assets.add(Asset("AXS", 40.5, 38.48,1558.44,R.drawable.axs_symbol))

        return assets
    }

    //generamos datos dummy con este método
    private fun getAssets_Activities(): MutableList<Asset_Activity>{
        var assets:MutableList<Asset_Activity> = ArrayList()

        assets.add(Asset_Activity("Receive", 0.5, "Juan","June 25",R.drawable.receive, "BNB"))
        assets.add(Asset_Activity("Transfer", 1.5, "Gustavo","June 25",R.drawable.send, "BTC"))
        assets.add(Asset_Activity("Transfer", 0.5, "Carlos","June 25",R.drawable.send,"BTC"))
        assets.add(Asset_Activity("Transfer", 1.5, "David","June 24",R.drawable.send, "BNB"))
        assets.add(Asset_Activity("Receive", 0.5, "Victor","June 24",R.drawable.receive," ETH"))
        assets.add(Asset_Activity("Receive", 3.6, "Andrea","June 23",R.drawable.receive, "BTC"))
        assets.add(Asset_Activity("Transfer", 2.5, "David","June 22",R.drawable.send,"ETH"))
        assets.add(Asset_Activity("Receive", 0.3, "David","June 22",R.drawable.receive, "BNB"))

        return assets
    }

    //configuramos lo necesario para desplegar el RecyclerView
    private fun setUpRecyclerView(){
        recyclerContacts.setHasFixedSize(true)
        //nuestro layout va a ser de una sola columna
        recyclerContacts.layoutManager = LinearLayoutManager(this)
        //seteando el Adapter
        mAdapter = RecyclerAdapter( this,getContacts())
        //asignando el Adapter al RecyclerView
        recyclerContacts.adapter = mAdapter
    }

    //configuramos lo necesario para desplegar el RecyclerView
    private fun setUpRecyclerView_Activity(){
        recyclerContacts.setHasFixedSize(true)
        //nuestro layout va a ser de una sola columna
        recyclerContacts.layoutManager = LinearLayoutManager(this)
        //seteando el Adapter
        mAdapter_activity = RecyclerAdapter_AssetActivity( this,getAssets_Activities())
        //asignando el Adapter al RecyclerView
        recyclerContacts.adapter = mAdapter_activity
    }
}