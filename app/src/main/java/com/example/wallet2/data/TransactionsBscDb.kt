package com.example.wallet2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wallet2.data.models.TransactionsBsc

@Database(entities = arrayOf(TransactionsBsc::class), version = 1)
abstract class TransactionsBscDb: RoomDatabase() {
    abstract fun transactionsBscDao(): TransactionsBscDao

    companion object {
        private var TransactionsBscInstance: TransactionsBscDb? = null

        const val DB_NAME = "TransactionsBsc_DB"

        fun getInstance(context: Context) : TransactionsBscDb?{
            if(TransactionsBscInstance==null){

                synchronized(TransactionsBscDb::class){
                    TransactionsBscInstance = Room.databaseBuilder(
                        context.applicationContext,
                        TransactionsBscDb::class.java,
                        DB_NAME)
                        .fallbackToDestructiveMigration() // al cambiar de version, destruir info en vez de migrar
                        .build()
                }
            }

            return TransactionsBscInstance
        }

    }
}