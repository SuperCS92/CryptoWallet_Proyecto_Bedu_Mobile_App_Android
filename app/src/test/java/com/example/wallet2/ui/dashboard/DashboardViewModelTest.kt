package com.example.wallet2.ui.dashboard

import com.example.wallet2.Asset
import com.example.wallet2.R
import junit.framework.TestCase

class DashboardViewModelTest : TestCase() {

    fun testGetTotalBalance() {

        val viewModel = DashboardViewModel()

        var assets:MutableList<Asset> = ArrayList()
        assets.add(Asset("Bitcoin", 0.5, 38000.0,1000.0, R.drawable.astr_btc_symbol, "BTC"))
        assets.add(Asset("Ether", 3.0, 2000.0,1000.0, R.drawable.astr_eth_symbol, "ETH"))
        assets.add(Asset("Cardano", 500.0, 1.33,1000.0, R.drawable.astr_cardano_symbol,"ADA"))
        assets.add(Asset("Tether", 50.0, 1.0,1000.0, R.drawable.astr_tether_symbol, "USDT"))
        assets.add(Asset("Binance", 7.0, 300.0,1000.0, R.drawable.astr_bnb_symbol,"BNB"))
        assets.add(Asset("xDai", 17.0, 1.0,1000.0, R.drawable.astr_xdai_symbol, "xDai"))
        assets.add(Asset("ChainLink", 35.0, 18.35,1000.0, R.drawable.astr_chainlink_symbol,"Link"))
        assets.add(Asset("AXS", 40.5, 38.48,1000.0, R.drawable.astr_axs_symbol, "AXS"))

        val result = viewModel.getTotalBalance(assets)

        assertEquals(8000.0, result)
    }
}