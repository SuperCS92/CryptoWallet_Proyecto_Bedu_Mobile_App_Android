package com.example.wallet2.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wallet2.data.models.User
import com.example.wallet2.data.userDb
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class UserViewModel:ViewModel() {


    protected val compositeDisposable = CompositeDisposable()

    private var dataBaseInstance: userDb?= null

    var personsList = MutableLiveData<List<User>>()

    fun setInstanceOfDb(dataBaseInstance: userDb) {
        this.dataBaseInstance = dataBaseInstance
    }

    fun saveDataIntoDb(data: User){

        dataBaseInstance?.personDataDao()?.insertUserData(data)
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