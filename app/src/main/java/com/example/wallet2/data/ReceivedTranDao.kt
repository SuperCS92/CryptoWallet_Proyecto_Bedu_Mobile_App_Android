package com.example.wallet2.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wallet2.data.models.ReceivedTran

@Dao
interface ReceivedTranDao {
    @Insert
    fun insertReceivedTran(receivedTran: ReceivedTran)

    @Update
    fun updateReceivedTran(receivedTran: ReceivedTran)

    @Query("SELECT * FROM received_trs")
    fun getReceivedTrans(): LiveData<List<ReceivedTran>>

    @Query("SELECT * FROM received_trs WHERE id = :id")
    fun getReceivedTranById(id: Int): ReceivedTran

    @Query("SELECT * FROM received_trs WHERE origin = :userIdOrigin")
    fun getReceivedTranByOrigin(userIdOrigin: String) : ReceivedTran
}