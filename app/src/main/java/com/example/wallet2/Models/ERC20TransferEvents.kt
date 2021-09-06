package com.example.binanceapi.Models

import com.google.gson.annotations.SerializedName

data class ERC20TransferEvents(
    @SerializedName("result")
    val transfers: List<Transfer>
)