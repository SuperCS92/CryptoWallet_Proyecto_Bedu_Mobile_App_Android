package com.example.wallet2.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wallet2.data.models.ReceivedTran

@Dao
interface ReceivedTranDao {
    @Insert
    suspend fun insertReceivedTran(vehicle: ReceivedTran)

    @Insert
    suspend fun insertAll(vehicles: List<ReceivedTran>)

    @Update
    suspend fun updateReceivedTran(vehicle: ReceivedTran)

    @Delete
    suspend fun removeReceivedTran(vehicle: ReceivedTran)

    @Query("DELETE FROM ReceivedTran WHERE id=:id")
    suspend fun removeReceivedTranById(id: Int)

    @Delete
    suspend fun removeReceivedTrans(vararg receivedTrans: ReceivedTran)

    @Query("SELECT * FROM ReceivedTran")
    fun getReceivedTrans(): LiveData<List<ReceivedTran>>

    @Query("SELECT * FROM ReceivedTran WHERE id = :id")
    suspend fun getReceivedTranById(id: Int): ReceivedTran

    @Query("SELECT * FROM ReceivedTran WHERE origin = :userIdOrigin")
    suspend fun getReceivedTranByOrigin(userIdOrigin: String) : ReceivedTran
}