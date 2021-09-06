package com.example.wallet2.Api

import com.example.binanceapi.Models.Account
import com.example.binanceapi.Models.ERC20Balance
import com.example.binanceapi.Models.ERC20TransferEvents
import com.example.binanceapi.Models.Transactions
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BscScanApi {
    @GET("/api")
    suspend fun getBalance(
        @Query("module") module: String,
        @Query("action") action: String,
        @Query("address") address: String,
        @Query("tag") tag: String,
        @Query("apikey") apikey: String,
    ): Response<Account>

    @GET("/api")
    suspend fun getTransactions(
        @Query("module") module: String,
        @Query("action") action: String,
        @Query("address") address: String,
        @Query("startblock") startblock: Int,
        @Query("endblock") endblock: Int,
        @Query("page") page: Int,
        @Query("offset") offset: Int,
        @Query("sort") sort: String,
        @Query("apikey") apikey: String,
    ): Response<Transactions>

    @GET("/api")
    suspend fun getERC20TokenTransferEvents(
        @Query("module") module: String,
        @Query("action") action: String,
        @Query("contractaddress") contractaddress: String,
        @Query("address") address: String,
        @Query("page") page: Int,
        @Query("offset") offset: Int,
        @Query("sort") sort: String,
        @Query("apikey") apikey: String,
    ): Response<ERC20TransferEvents>

    @GET("/api")
    suspend fun getERC20TokenBalance(
        @Query("module") module: String,
        @Query("action") action: String,
        @Query("contractaddress") contractaddress: String,
        @Query("address") address: String,
        @Query("tag") tag: String,
        @Query("apikey") apikey: String,
    ): Response<ERC20Balance>
}