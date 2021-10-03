package com.example.wallet2.ui.send

import androidx.lifecycle.ViewModel
import com.example.wallet2.data.SendDb
import com.example.wallet2.data.models.SendTran
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SendViewModel(): ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    private var dataBaseInstance: SendDb? = null

    /*fun insertTransfer(transfer: SendTran) = viewModelScope.launch{
        sendRepository?.insertTransfer(transfer)
    }*/

    fun setInstanceOfDb(dataBaseInstance: SendDb) {
        this.dataBaseInstance = dataBaseInstance
    }

    fun insertTransfer(transfer: SendTran) {

        dataBaseInstance?.sendDao()?.insertTransfer(transfer)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
            },{

            })?.let {
                compositeDisposable.add(it)
            }
    }

    /*fun removeTransfer(transfer: SendTran) = viewModelScope.launch{
        sendRepository?.removeTransfer(transfer)
    }*/


}