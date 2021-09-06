package com.example.binanceapi.Models

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class ERC20Balance(
    @SerializedName("result")
    val balance: BigInteger
)
