package com.example.wallet2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wallet2.data.models.SendTran

@Database(entities = [SendTran::class], version = 1)
abstract class SendDb: RoomDatabase() {

    companion object {

        private const val DB_NAME_SEND = "USER_TRANSFERS"

        @Volatile
        private var sendInstance: SendDb? = null

        fun getInstance(context: Context): SendDb {

            /*val instance = synchronized(this) {
                    Room.databaseBuilder(
                    context.applicationContext,
                    SendDb::class.java,
                    DB_NAME_SEND)
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return sendInstance?: instance*/
            return sendInstance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SendDb::class.java,
                    DB_NAME_SEND
                )
                    .fallbackToDestructiveMigration()
                    .build()
                sendInstance = instance
                // return instance
                instance
            }

        }
    }

    abstract fun sendDao(): SendDao
}