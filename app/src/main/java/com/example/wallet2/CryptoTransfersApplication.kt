package com.example.wallet2

import android.app.Application
import com.example.wallet2.Repository.CryptoTransferRepository
import com.example.wallet2.data.CryptoTransferDb

class CryptoTransfersApplication: Application() {
    val cryptoTransactionRepository: CryptoTransferRepository
        get() = CryptoTransferRepository(
            CryptoTransferDb.getDatabase(this)!!.cryptoTransferDao()
        )
}