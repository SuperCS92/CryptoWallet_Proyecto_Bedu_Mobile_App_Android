package com.example.binanceapi.Models

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class Account(
    @SerializedName("result")
    val balance: BigInteger
)
