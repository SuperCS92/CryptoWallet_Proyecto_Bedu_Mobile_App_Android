package com.example.wallet2.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wallet2.Asset
import com.example.wallet2.Asset_Activity
import com.example.wallet2.R
import kotlin.math.round

class DashboardViewModel: ViewModel() {

    private val _assets = MutableLiveData<MutableList<Asset>>()
    val assets : LiveData<MutableList<Asset>>
        get() = _assets

    private val _activities = MutableLiveData<MutableList<Asset_Activity>>()
    val activities: LiveData<MutableList<Asset_Activity>>
        get() = _activities

    fun getAssets(){
        val a = getContacts()
        _assets.value = a
    }

    fun getActivities(){
        val a = getAssets_Activities()
        _activities.value = a
    }



    fun getTotalBalance(list: MutableList<Asset>): Double {
        var total: Double = 0.0

        list.forEach { total += it.balance_fiat}

        return total
    }

    //generamos datos dummy con este método
    private fun getContacts(): MutableList<Asset>{
        var assets:MutableList<Asset> = ArrayList()
        var balance: List<Double> = listOf(0.5,3.0,7.0)
        var fiatPrice: List<Double> = listOf(61853.30,4539.70,644.87)

        assets.add(Asset("Bitcoin", balance[0], fiatPrice[0],round(fiatPrice[0]*balance[0]), R.drawable.astr_btc_symbol, "BTC"))
        assets.add(Asset("Ether", balance[1], fiatPrice[1],round(fiatPrice[1]*balance[1]), R.drawable.astr_eth_symbol, "ETH"))
        //assets.add(Asset("Cardano", 500.0, 1.33,665.0, R.drawable.astr_cardano_symbol,"ADA"))
        //assets.add(Asset("Tether", 50.0, 1.0,50.0, R.drawable.astr_tether_symbol, "USDT"))
        assets.add(Asset("Binance", balance[2], fiatPrice[2],round(fiatPrice[2]*balance[2]), R.drawable.astr_bnb_symbol,"BNB"))
        //assets.add(Asset("xDai", 17.0, 1.0,17.0, R.drawable.astr_xdai_symbol, "xDai"))
        //assets.add(Asset("ChainLink", 35.0, 18.35,642.25, R.drawable.astr_chainlink_symbol,"Link"))
        //assets.add(Asset("AXS", 40.5, 38.48,1558.44, R.drawable.astr_axs_symbol, "AXS"))

        return assets
    }

    //generamos datos dummy con este método
    private fun getAssets_Activities() = mutableListOf(
    Asset_Activity("Receive", 0.5, "Juan","June 25",R.drawable.astr_receive_icon, "BNB"),
    Asset_Activity("Transfer", 1.5, "Gustavo","June 25",R.drawable.astr_send_icon, "BTC"),
    Asset_Activity("Transfer", 0.5, "Carlos","June 25",R.drawable.astr_send_icon,"BTC"),
    Asset_Activity("Transfer", 1.5, "David","June 24",R.drawable.astr_send_icon, "BNB"),
    Asset_Activity("Receive", 0.5, "Victor","June 24",R.drawable.astr_receive_icon," ETH"),
    Asset_Activity("Receive", 3.6, "Andrea","June 23",R.drawable.astr_receive_icon, "BTC"),
    Asset_Activity("Transfer", 2.5, "David","June 22",R.drawable.astr_send_icon,"ETH"),
    Asset_Activity("Receive", 0.3, "David","June 22",R.drawable.astr_receive_icon, "BNB")
    )
}