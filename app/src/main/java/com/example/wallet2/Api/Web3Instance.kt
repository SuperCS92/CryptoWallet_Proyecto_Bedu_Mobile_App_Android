package com.example.wallet2.Api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.wallet2.utils.Constants.Companion.BSC_TESTNET_URL
import com.example.wallet2.utils.Constants.Companion.PRIVATE_KEY
import org.web3j.crypto.Credentials
import org.web3j.crypto.ECKeyPair
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.protocol.http.HttpService
import org.web3j.tx.Transfer
import org.web3j.utils.Convert
import java.lang.Exception
import java.math.BigDecimal
import java.math.BigInteger
import java.util.concurrent.TimeUnit

object Web3Instance {
    private val ethWeb3Client by lazy {
        Web3j.build( HttpService(BSC_TESTNET_URL))
    }

    val transactionReceipt: MutableLiveData<TransactionReceipt> = MutableLiveData()


    @Throws(Exception::class)
    suspend fun transfer(toAddress:String, value: BigDecimal)  {
        Log.d("Response", "Web3Instance ")

        try {
            Log.d("Response", "Web3Instance insdie try ")

            var tx = Transfer.sendFunds(
                ethWeb3Client,
                getCredentials(),
                toAddress,
                value,
                Convert.Unit.ETHER
            ).sendAsync().whenComplete { t, u ->
                Log.d("tx:", t.transactionHash)
                transactionReceipt.value = t
            }

        } catch (e: Exception) {
            Log.d("tx:", e.toString())
        }
    }

    fun getCredentials(): Credentials {

        return Credentials
            .create(PRIVATE_KEY)
    }

    fun getEckeyPair(): ECKeyPair {
        return getCredentials().ecKeyPair
    }

    suspend fun getBalance(addr:String): BigInteger {
        val ethgetbalance = ethWeb3Client
            .ethGetBalance(addr, DefaultBlockParameter.valueOf("latest"))
            .sendAsync()
            .get(10, TimeUnit.SECONDS)

        return ethgetbalance.balance
    }

}