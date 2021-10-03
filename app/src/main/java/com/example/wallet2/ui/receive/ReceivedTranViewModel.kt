package com.example.wallet2.ui.receive

import androidx.lifecycle.ViewModel
import com.example.wallet2.data.ReceivedTranDb
import com.example.wallet2.data.models.ReceivedTran
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ReceivedTranViewModel: ViewModel() {


    protected val compositeDisposable = CompositeDisposable()

    private var dataBaseInstance: ReceivedTranDb?= null

    fun setInstanceOfDb(dataBaseInstance: ReceivedTranDb) {
        this.dataBaseInstance = dataBaseInstance
    }

    fun saveDataIntoDb(data: ReceivedTran){

        dataBaseInstance?.receivedTranDao()?.insertReceivedTran(data)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({
            },{

            })?.let {
                compositeDisposable.add(it)
            }
    }

    fun updateDataIntoDb(data: ReceivedTran){

        dataBaseInstance?.receivedTranDao()?.updateReceivedTran(data)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({
            },{

            })?.let {
                compositeDisposable.add(it)
            }
    }


    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }



}