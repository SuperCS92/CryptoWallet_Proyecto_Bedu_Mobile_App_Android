package com.example.wallet2.Repository

import com.example.wallet2.data.CryptoTransferDao
import com.example.wallet2.data.models.CryptoTransfer
import kotlinx.coroutines.*

class CryptoTransferRepository(
    private val cryptoTransferDao: CryptoTransferDao
) {
    suspend fun addCryptoTransfer(cryptoTransfer: CryptoTransfer) {
        coroutineScope {
            launch{cryptoTransferDao.insertCryptoTransfer(cryptoTransfer)}
        }
    }

//    suspend fun updateReceivedTran(receivedTran: ReceivedTran) {
//        coroutineScope {
//            launch{receivedTranDao.updateReceivedTran(receivedTran)}
//        }
//    }


}