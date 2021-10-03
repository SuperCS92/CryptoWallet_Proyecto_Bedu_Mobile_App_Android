package com.example.wallet2.Repository

import com.example.wallet2.data.ReceivedTranDao
import com.example.wallet2.data.models.ReceivedTran
import kotlinx.coroutines.*

class ReceivedTranRepository(
    private val receivedTranDao: ReceivedTranDao
) {
    suspend fun addReceivedTran(receivedTran: ReceivedTran) {
        coroutineScope {
            launch{receivedTranDao.insertReceivedTran(receivedTran)}
        }
    }

    suspend fun updateReceivedTran(receivedTran: ReceivedTran) {
        coroutineScope {
            launch{receivedTranDao.updateReceivedTran(receivedTran)}
        }
    }


}