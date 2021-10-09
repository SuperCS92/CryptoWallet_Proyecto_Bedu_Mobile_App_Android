package com.example.wallet2.Api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.wallet2.utils.Constants.Companion.BSC_TESTNET_URL
import com.example.wallet2.utils.Constants.Companion.PRIVATE_KEY
import org.bitcoinj.params.TestNet3Params
import org.bitcoinj.script.Script
import org.bitcoinj.wallet.Wallet
import org.web3j.crypto.*
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
    suspend fun transfer(mnemonic: String,toAddress:String, value: BigDecimal)  {
        Log.d("Response", "Web3Instance ")

        try {
            Log.d("Response", "Web3Instance insdie try ")

            var tx = Transfer.sendFunds(
                ethWeb3Client,
                getCredentials(mnemonic),
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

    suspend fun getBalance(addr:String): BigInteger {
        val ethgetbalance = ethWeb3Client
            .ethGetBalance(addr, DefaultBlockParameter.valueOf("latest"))
            .sendAsync()
            .get(10, TimeUnit.SECONDS)

        return ethgetbalance.balance
    }

   private fun generateSeed(): String{

        val params = TestNet3Params.get()
        val wallet = Wallet.createDeterministic(params, Script.ScriptType.P2PKH)
        val seed = wallet.keyChainSeed

        return seed.mnemonicCode.toString()
    }

    private fun getCredentials(mnemonic:String, password:String=""):Credentials{

        val seed = MnemonicUtils.generateSeed(mnemonic, password)
        val masterKeypair = Bip32ECKeyPair.generateKeyPair(seed)
        val path = intArrayOf(44 or Bip32ECKeyPair.HARDENED_BIT, 60 or Bip32ECKeyPair.HARDENED_BIT, 0 or Bip32ECKeyPair.HARDENED_BIT, 0, 0)
        val childKeypair = Bip32ECKeyPair.deriveKeyPair(masterKeypair, path)
        val credentials: Credentials = Credentials.create(childKeypair)

        return credentials
    }

    private fun getAddress(mnemonic:String, password:String=""):String{
        val credentials = getCredentials(mnemonic,password)

        val addr = Keys.toChecksumAddress(credentials.address)

        return addr
    }



}