package com.example.wallet2.data

import androidx.room.*
import com.example.wallet2.data.models.TransactionsBsc

@Dao
interface TransactionsBscDao {
    @Insert
    fun insertTransaction(transactionBsc: TransactionsBsc)

    @Update
    fun updateTransaction(transactionBsc: TransactionsBsc)

    @Delete
    fun removeTransaction(transactionBsc: TransactionsBsc)

    @Query("DELETE FROM TransactionsBsc WHERE hash=:hash")
    fun removeTransactionByHash(hash: String)

    @Delete
    fun removeTransactions(vararg transactionBsc: TransactionsBsc)

    @Query("SELECT * FROM TransactionsBsc")
    fun getTransactionsBsc(): List<TransactionsBsc>

    @Query("SELECT * FROM TransactionsBsc WHERE contract_address = :contractAddress")
    fun getTransactionsBscByContractAddress(contractAddress: String): List<TransactionsBsc>

    @Query("SELECT * FROM TransactionsBsc WHERE sender= :from")
    fun getTransactionsBscByFrom(from: String): List<TransactionsBsc>
}