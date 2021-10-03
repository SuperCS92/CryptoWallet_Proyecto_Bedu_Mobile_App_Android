package com.example.wallet2.Repository

import androidx.lifecycle.LiveData
import com.example.wallet2.data.SendDao
import com.example.wallet2.data.models.SendTran
import kotlinx.coroutines.*

class SendRepository(private val sendDao: SendDao) {

    suspend fun insertTransfer(transfer: SendTran) {
        coroutineScope {
            launch {sendDao.insertTransfer(transfer)}
        }
    }

    suspend fun removeTransfer(transfer: SendTran){
        coroutineScope {
            launch {sendDao.removeTransfer(transfer)}
        }
    }

    suspend fun getAll(): List<SendTran> {
        return sendDao.getAll()
    }

}