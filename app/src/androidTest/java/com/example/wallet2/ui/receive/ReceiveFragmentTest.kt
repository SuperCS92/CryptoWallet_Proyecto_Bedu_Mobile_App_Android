package com.example.wallet2.ui.receive

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wallet2.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReceiveFragmentTest {
    @Test
    fun testEventGenerateQrFragment() {
        val scenario = launchFragmentInContainer<ReceiveFragment>()
        onView(withId(R.id.qrGenerateBtn))
            .perform(click())
    }

    @Test
    fun testEventDownloadFragment() {
        val scenario = launchFragmentInContainer<ReceiveFragment>()
        onView(withId(R.id.astr_transaction_download_btn))
            .perform(click())
    }
}

