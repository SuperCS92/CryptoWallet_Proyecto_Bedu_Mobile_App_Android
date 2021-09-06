package com.example.wallet2.Api

import com.example.wallet2.utils.Constants.Companion.BASE_URL
import com.example.wallet2.utils.Constants.Companion.BSC_SCAN_URL
import com.example.wallet2.utils.Constants.Companion.ETHERSCAN_MAINNET_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val etherscanretrofit by lazy{
        Retrofit.Builder()
            .baseUrl(ETHERSCAN_MAINNET_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val bscscanretrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BSC_SCAN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api:BinanceApi by lazy {
        retrofit.create(BinanceApi::class.java)
    }

    val etherscanapi:EtherScanApi by lazy {
        etherscanretrofit.create(EtherScanApi::class.java)
    }

    val bscscanapi: BscScanApi by lazy {
        bscscanretrofit.create(BscScanApi::class.java)
    }
}