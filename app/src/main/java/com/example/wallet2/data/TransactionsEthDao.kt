package com.example.wallet2.data

import androidx.room.*
import com.example.wallet2.data.models.TransactionsEth

@Dao
interface TransactionsEthDao {
    @Insert
    fun insertTransaction(transactionEth: TransactionsEth)

    @Update
    fun updateTransaction(transactionEth: TransactionsEth)

    @Delete
    fun removeTransaction(transactionEth: TransactionsEth)

    @Query("DELETE FROM TransactionsEth WHERE hash=:hash")
    fun removeTransactionByHash(hash: String)

    @Delete
    fun removeTransactions(vararg transactionEth: TransactionsEth)

    @Query("SELECT * FROM TransactionsEth")
    fun getTransactionsEth(): List<TransactionsEth>

    @Query("SELECT * FROM TransactionsEth WHERE contract_address = :contractAddress")
    fun getTransactionsEthByContractAddress(contractAddress: String): List<TransactionsEth>

    @Query("SELECT * FROM TransactionsEth WHERE sender= :from")
    fun getTransactionsEthByFrom(from: String): List<TransactionsEth>


}