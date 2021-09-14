package com.example.wallet2.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionsEth(
    @PrimaryKey val hash: String,
    @ColumnInfo(name="block_hash") val blockHash: String,
    @ColumnInfo(name="block_number") val blockNumber: String,
    @ColumnInfo val confirmations: String,
    @ColumnInfo(name="contract_address") val contractAddress: String,
    @ColumnInfo(name="cumulative_gas_used") val cumulativeGasUsed: String,
    @ColumnInfo(name="sender") val from: String,
    @ColumnInfo val gas: String,
    @ColumnInfo(name="gas_price") val gasPrice: String,
    @ColumnInfo(name="gas_used") val gasUsed: String,
    @ColumnInfo val input: String,
    @ColumnInfo(name="is_error") val isError: String,
    @ColumnInfo val nonce: String,
    @ColumnInfo(name="time_stamp") val timeStamp: String,
    @ColumnInfo val to: String,
    @ColumnInfo(name="transaction_index") val transactionIndex: String,
    @ColumnInfo(name="tx_receipt_status") val txreceipt_status: String,
    @ColumnInfo val value: String
)
