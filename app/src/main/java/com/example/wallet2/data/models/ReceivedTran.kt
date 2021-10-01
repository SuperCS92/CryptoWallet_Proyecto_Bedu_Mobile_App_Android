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
    val userIdOrigin: String,

    @ColumnInfo(name = "hash")
    val hash: String,

    @ColumnInfo(name = "createdAt")
    val createdAt: String

)