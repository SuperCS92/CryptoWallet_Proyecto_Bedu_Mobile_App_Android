package com.example.wallet2.Api

import com.example.binanceapi.Models.Symbol
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BinanceApi {
    @GET("ticker/price")
    suspend fun getPrice(
        @Query("symbol") symbol: String
    ): Response<Symbol>
}