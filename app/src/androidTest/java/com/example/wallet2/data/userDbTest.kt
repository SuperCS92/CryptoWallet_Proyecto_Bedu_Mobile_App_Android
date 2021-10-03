package com.example.wallet2.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wallet2.data.models.User
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class userDbTest: TestCase() {

    private lateinit var db: userDb
    private lateinit var dao: UserDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, userDb::class.java).build()
        db.personDataDao()
    }

    @After
    fun closeDatabase() {
        db.close()
    }

    @Test
    fun writeAndReadUser() = runBlocking {
        val user = User(0, "Nombre prueba", "prueba@gmail.com", "prueba", "pass1234", "seed_test")
        dao.insertUserData(user)

        val readUser = dao.getLast20Users()
        assertThat(readUser.contains(user)).isTrue()
    }


}