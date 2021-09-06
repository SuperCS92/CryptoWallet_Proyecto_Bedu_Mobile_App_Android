package com.example.wallet2.Repository

import android.util.Log
import com.example.binanceapi.Models.*
import com.example.wallet2.Api.RetrofitInstance
import com.example.wallet2.Api.Web3Instance
import retrofit2.Response
import java.math.BigDecimal
import java.math.BigInteger

class Repository {
    suspend fun getSymbol(symbol:String): Response<Symbol> {
        return RetrofitInstance.api.getPrice(symbol)
    }


    //------------------------ETH API functions -------------------//

    suspend fun getBalanceEth (module: String,
                               action: String,
                               address: String,
                               tag: String,
                               apikey: String,
    ): Response<Account> {
        return RetrofitInstance
            .etherscanapi
            .getBalance(
                module,
                action,
                address,
                tag,
                apikey
            )
    }

    suspend fun getTransactionsEth (
        module: String,
        action: String,
        address: String,
        startblock: Int,
        endblock: Int,
        page: Int,
        offset: Int,
        sort: String,
        apikey: String,
    ): Response<Transactions> {
        return RetrofitInstance
            .etherscanapi
            .getTransactions(
                module,
                action,
                address,
                startblock,
                endblock,
                page,
                offset,
                sort,
                apikey
            )
    }

    suspend fun getERC20TokenTransferEventsEth(
        module: String,
        action: String,
        contractaddress: String,
        address: String,
        page: Int,
        offset: Int,
        sort: String,
        apikey: String,
    ): Response<ERC20TransferEvents> {
        return RetrofitInstance
            .etherscanapi
            .getERC20TokenTransferEvents(
                module,
                action,
                contractaddress,
                address,
                page,
                offset,
                sort,
                apikey
            )
    }

    suspend fun getERC20TokenBalanceEth(
        module: String,
        action: String,
        contractaddress: String,
        address: String,
        tag: String,
        apikey: String,
    ): Response<ERC20Balance> {
        return RetrofitInstance
            .etherscanapi
            .getERC20TokenBalance(
                module,
                action,
                contractaddress,
                address,
                tag,
                apikey
            )
    }

    suspend fun getERC20TokenInfoEth(
        module: String,
        action: String,
        contractaddress: String,
        apikey: String,
    ): Response<ERC20Info> {
        return RetrofitInstance
            .etherscanapi
            .getERC20TokenInfo(
                module,
                action,
                contractaddress,
                apikey
            )
    }


    //------------------------BSC API functions -------------------//

    suspend fun getBalanceBsc (module: String,
                               action: String,
                               address: String,
                               tag: String,
                               apikey: String,
    ): Response<Account> {
        return RetrofitInstance
            .bscscanapi
            .getBalance(
                module,
                action,
                address,
                tag,
                apikey
            )
    }

    suspend fun getTransactionsBsc (
        module: String,
        action: String,
        address: String,
        startblock: Int,
        endblock: Int,
        page: Int,
        offset: Int,
        sort: String,
        apikey: String,
    ): Response<Transactions> {
        return RetrofitInstance
            .bscscanapi
            .getTransactions(
                module,
                action,
                address,
                startblock,
                endblock,
                page,
                offset,
                sort,
                apikey
            )
    }

    suspend fun getERC20TokenTransferEventsBsc(
        module: String,
        action: String,
        contractaddress: String,
        address: String,
        page: Int,
        offset: Int,
        sort: String,
        apikey: String,
    ): Response<ERC20TransferEvents> {
        return RetrofitInstance
            .bscscanapi
            .getERC20TokenTransferEvents(
                module,
                action,
                contractaddress,
                address,
                page,
                offset,
                sort,
                apikey
            )
    }

    suspend fun getERC20TokenBalanceBsc(
        module: String,
        action: String,
        contractaddress: String,
        address: String,
        tag: String,
        apikey: String,
    ): Response<ERC20Balance> {
        return RetrofitInstance
            .bscscanapi
            .getERC20TokenBalance(
                module,
                action,
                contractaddress,
                address,
                tag,
                apikey
            )
    }

    suspend fun transfer(toAddress:String, value: BigDecimal){
        Log.d("Response", "Repository ")
        Web3Instance.transfer(toAddress, value)
    }

    fun getAddress():String{
        var credential = Web3Instance.getCredentials()
        var addr = credential.address

        return addr
    }

    suspend fun getBalance(address: String): BigInteger {
        return Web3Instance.getBalance(address)
    }
}