package com.example.binanceapi.Models

import com.google.gson.annotations.SerializedName

data class Transactions(
    @SerializedName("result")
    val Transactions: List<Tx>
)

