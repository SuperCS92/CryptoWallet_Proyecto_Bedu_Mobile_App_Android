package com.example.binanceapi.Models

import com.google.gson.annotations.SerializedName

data class ERC20Info3(
    @SerializedName("result")
    val info: List<Info>,
)