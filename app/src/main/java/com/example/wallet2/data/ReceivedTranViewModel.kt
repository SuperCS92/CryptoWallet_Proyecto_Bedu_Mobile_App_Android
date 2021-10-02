package com.example.wallet2.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.RoomDatabase
import com.example.wallet2.data.models.ReceivedTran
import kotlinx.coroutines.launch

class ReceivedTranViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ReceivedTranRepository
    init {
        val receivedTranDB = ReceivedTranDb.getDatabase(application).receivedTranDao()
        repository = ReceivedTranRepository(receivedTranDB)
    }


    fun saveReceivedTran(receivedTran: ReceivedTran) =viewModelScope.launch {
        repository.addReceivedTran(receivedTran)
//        receivedTranRepository.addReceivedTran(receivedTran)
    }

    fun updateReceivedTran(receivedTran: ReceivedTran) =viewModelScope.launch {
        repository.updateReceivedTran(receivedTran)
//        receivedTranRepository.addReceivedTran(receivedTran)
    }
}