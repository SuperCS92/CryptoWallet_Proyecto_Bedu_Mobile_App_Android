package com.example.wallet2.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "received_trs", indices = [Index(value = ["id"], unique = true)])
data class ReceivedTran(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "amount")
    val amount: Float,

    @ColumnInfo(name = "origin")
    val userIdOrigin: Int,

    @ColumnInfo(name = "asset")
    val asset: String,

    @ColumnInfo(name = "createdAt")
    val createdAt: String,

    @ColumnInfo(name = "nameQr")
    val nameQr: String,

    @ColumnInfo(name = "status")
    val status: String

)