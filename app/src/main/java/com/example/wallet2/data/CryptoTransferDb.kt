package com.example.wallet2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wallet2.data.models.CryptoTransfer

@Database(entities = [CryptoTransfer::class], version = 1)
abstract class CryptoTransferDb: RoomDatabase() {
    abstract fun cryptoTransferDao(): CryptoTransferDao
    companion object {

        private const val DB_NAME_TRANFER = "crypto_transfers"

        @Volatile
        private var INSTANCE: CryptoTransferDb? = null

        fun getDatabase(context: Context): CryptoTransferDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CryptoTransferDb::class.java,
                    DB_NAME_TRANFER
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}
