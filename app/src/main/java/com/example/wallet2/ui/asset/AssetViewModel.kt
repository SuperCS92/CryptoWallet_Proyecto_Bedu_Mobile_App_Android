package com.example.wallet2.ui.asset

import androidx.lifecycle.ViewModel
import com.example.wallet2.Repository.CryptoTransferRepository

class AssetViewModel(private val cryptoTransferRepository: CryptoTransferRepository): ViewModel() {
    val cryptoTransferList = cryptoTransferRepository.getCryptoTransfers()
}