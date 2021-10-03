package com.example.wallet2.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wallet2.TestUtil
import com.example.wallet2.data.models.ReceivedTran
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ReceivedTranDbTest {
    private lateinit var receivedTranDao: ReceivedTranDao
    private lateinit var db: ReceivedTranDb

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ReceivedTranDb::class.java).build()
        receivedTranDao = db.receivedTranDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeReceivedTranAndReadInListFromDB() {
        // Test to verify create instance in BD
        val rtran: ReceivedTran = TestUtil.createReceivedTran(3)
        receivedTranDao.insertReceivedTran(rtran)
        val byID = receivedTranDao.getReceivedTranById(3)
        assertEquals(byID, rtran)
    }
}