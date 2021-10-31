package com.example.wallet2.ui.receive

import android.util.Log
import java.text.SimpleDateFormat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallet2.Repository.CryptoTransferRepository
import com.example.wallet2.data.models.CryptoTransfer
import kotlinx.coroutines.launch
import java.util.*

class CrytoTransferViewModel(private val cryptoTransferRepository: CryptoTransferRepository): ViewModel() {
    private var _cryptoTransferDone = MutableLiveData<Boolean>(false)
    val cryptoTransferDone = _cryptoTransferDone


    var amount: String? = null
    var asset: String? = null

    fun setAmount(s: CharSequence, start:Int, before: Int, count:Int){
        amount = s.toString()
    }

    fun setAsset(s: CharSequence, start:Int, before: Int, count:Int){
        asset = s.toString()
    }

    fun getCurrentDateTime(): Date {
        // Method to obtain date at the moment of creating a transaction
        return Calendar.getInstance().time
    }
    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        // Method to convert date in string so it could be saved in DB as String
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun newCryptoTransfer() = viewModelScope.launch{
        if ( !amount.isNullOrBlank() && !asset.isNullOrBlank()){
            Log.d("amount", amount.toString())
            Log.d("asset", asset.toString())
            val date = getCurrentDateTime()
            val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")
            val cryptoWallet = CryptoTransfer(
                amount = amount.toString(),
                asset = asset.toString(),
                userIdOrigin = 0,
                createdAt = dateInString,
                nameQr = "",
                status = "generated",
                id = 0
            )

            cryptoTransferRepository.addCryptoTransfer(cryptoWallet)
//
            _cryptoTransferDone.value = true
        }
    }

}


//    protected val compositeDisposable = CompositeDisposable()
//
//    private var dataBaseInstance: ReceivedTranDb?= null
//
//    fun setInstanceOfDb(dataBaseInstance: ReceivedTranDb) {
//        this.dataBaseInstance = dataBaseInstance
//    }

//    fun saveDataIntoDb(data: ReceivedTran){
//
//        dataBaseInstance?.receivedTranDao()?.insertReceivedTran(data)
//            ?.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.subscribe ({
//            },{
//
//            })?.let {
//                compositeDisposable.add(it)
//            }
//    }

//    fun updateDataIntoDb(data: ReceivedTran){
//
//        dataBaseInstance?.receivedTranDao()?.updateReceivedTran(data)
//            ?.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.subscribe ({
//            },{
//
//            })?.let {
//                compositeDisposable.add(it)
//            }
//    }

//    fun getByIdReceive(id: Int){
//        dataBaseInstance?.receivedTranDao()?.getReceivedTranById(id)
//    }


//    override fun onCleared() {
//        compositeDisposable.dispose()
//        compositeDisposable.clear()
//        super.onCleared()
//    }



//}