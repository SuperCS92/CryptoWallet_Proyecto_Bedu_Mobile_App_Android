package com.example.wallet2.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wallet2.data.models.CryptoTransfer
import io.reactivex.Completable

@Dao
interface CryptoTransferDao {
    @Insert
    fun insertCryptoTransfer(cryptoTransfer: CryptoTransfer)

//    @Update
//    fun updateReceivedTran(receivedTran: ReceivedTran): Completable
//
//    @Query("SELECT * FROM received_trs WHERE id = :id")
//    fun getReceivedTranById(id: Int): ReceivedTran
//
//    @Query("SELECT * FROM received_trs WHERE origin = :userIdOrigin")
//    fun getReceivedTranByOrigin(userIdOrigin: String) : ReceivedTran
}