package com.example.wallet2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wallet2.data.models.TransactionsEth

@Database(entities = arrayOf(TransactionsEth::class), version = 1)
abstract class TransactionsEthDb: RoomDatabase() {

    abstract fun transactionsEthDao(): TransactionsEthDao


    companion object {
        private var TransactionsEthInstance: TransactionsEthDb? = null

        const val DB_NAME = "TransactionsEthreum_DB"

        fun getInstance(context: Context) : TransactionsEthDb?{
            if(TransactionsEthInstance==null){

                synchronized(TransactionsEthDb::class){
                    TransactionsEthInstance = Room.databaseBuilder(
                        context.applicationContext,
                        TransactionsEthDb::class.java,
                        DB_NAME)
                        .fallbackToDestructiveMigration() // al cambiar de version, destruir info en vez de migrar
                        .build()
                }
            }

            return TransactionsEthInstance
        }

    }
}