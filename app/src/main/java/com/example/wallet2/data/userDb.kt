package com.example.wallet2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wallet2.data.models.User

@Database(entities = [User::class], version = DB_VERSION)
abstract class userDb : RoomDatabase() {
    abstract fun personDataDao(): UserDao

    companion object {
        @Volatile
        private var databseInstance: userDb? = null

        fun getDatabasenIstance(mContext: Context): userDb =
            databseInstance ?: synchronized(this) {
                databseInstance ?: buildDatabaseInstance(mContext).also {
                    databseInstance = it
                }
            }

        private fun buildDatabaseInstance(mContext: Context) =
            Room.databaseBuilder(mContext, userDb::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()

    }
}


const val DB_VERSION = 1

const val DB_NAME = "UserData.db"