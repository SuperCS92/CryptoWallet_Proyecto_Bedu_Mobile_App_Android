package com.example.wallet2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wallet2.data.models.ReceivedTran
import com.example.wallet2.data.models.User

@Database(entities = [ReceivedTran::class], version = 1)
abstract class ReceivedTranDb: RoomDatabase() {
    abstract fun receivedTranDao(): ReceivedTranDao
    companion object {

        private const val DB_NAME_RTRAN = "receivedtrs_db"

        @Volatile
        private var INSTANCE: ReceivedTranDb? = null

        fun getDatabase(context: Context): ReceivedTranDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReceivedTranDb::class.java,
                    DB_NAME_RTRAN
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
