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

    @Query("SELECT * FROM user_table WHERE email = :email")
    fun getSeed(email: String): User

    @Query("SELECT * FROM user_table ORDER BY username DESC LIMIT 20")
    fun getLast20Users(): List<User>

    @Delete
    fun deleteUser(person: User) : Completable

    @Update
    fun updateUser(person: User)
}