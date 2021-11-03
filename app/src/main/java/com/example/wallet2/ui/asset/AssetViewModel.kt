package com.example.wallet2.ui.asset

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.wallet2.Repository.CryptoTransferRepository
import com.example.wallet2.data.models.CryptoTransfer

class AssetViewModel(private val cryptoTransferRepository: CryptoTransferRepository): ViewModel() {
    val cryptoTransferList = cryptoTransferRepository.getCryptoTransfers()
}