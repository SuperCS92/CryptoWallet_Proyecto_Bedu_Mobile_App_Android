package com.example.wallet2.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wallet2.data.models.User
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserData(user: User) : Completable

    @Query("SELECT * FROM user_table WHERE username = :user and password = :pass")
    fun getUser(user: String, pass: String): User

    @Delete
    fun deleteUser(person: User) : Completable

    @Update
    fun updateUser(person: User)
}