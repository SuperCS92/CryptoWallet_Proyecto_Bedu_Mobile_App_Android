package com.example.wallet2.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user_transfers", indices = [Index(value = ["id"], unique = true)])
data class SendTran constructor(
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    @ColumnInfo(name = "asset_type") val assetType: String,
    @ColumnInfo(name = "amount") val totalAmount: Double,
    @ColumnInfo(name = "email") val emailAddress: String,
    @ColumnInfo(name = "fee") val feeValue: Float,
    @ColumnInfo(name = "total") val totalTransfer: Float
)