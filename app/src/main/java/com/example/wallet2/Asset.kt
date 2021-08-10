package com.example.wallet2

data class Asset (val name:String,
                  var balance: Double,
                  var fiat_price: Double,
                  var balance_fiat:Double,
                  var idImage: Int,
                  var symbol:String)