package com.example.wallet2.ui.send

import com.example.wallet2.ui.dashboard.DashboardViewModel
import com.google.common.truth.Truth
import org.junit.Test
import org.junit.Assert.*

class SendFragmentTest {

    private val sendObject = SendFragment()

    // Test 1: non-empty data
    @Test
    fun dataIsNotEmpty() {
        // GIVEN
        val asset = "BTC"
        val amount = 400.0
        val address = "0xbedu@org.com"
        // WHEN
        val result = sendObject.validateInformation(asset,amount,address)
        // THEN
        Truth.assertThat(result).isEqualTo(true)
    }

    // Test 2: empty data
    @Test
    fun dataIsEmpty() {
        // GIVEN
        val asset = ""
        val amount = 0.0
        val address = ""
        // WHEN
        val result = sendObject.validateInformation(asset,amount,address)
        // THEN
        Truth.assertThat(result).isEqualTo(false)
    }

    // Test 3: empty data and not-empty data
    @Test
    fun dataValidation() {
        // GIVEN
        val asset = ""
        val amount = 300.23
        val address = "bedu@org.com"
        // WHEN
        val result = sendObject.validateInformation(asset,amount,address)
        // THEN
        Truth.assertThat(result).isEqualTo(false)
    }

}