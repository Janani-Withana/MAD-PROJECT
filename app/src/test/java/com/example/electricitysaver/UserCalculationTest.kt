

package com.example.electricitysaver

import com.example.electricitysaver.testing.UserItemCalculationsValidator
import org.junit.Assert.assertEquals
import org.junit.Test

class UserCalculationTest {

    // Test the calculateMonthlyUnit function
    @Test
    fun calculateMonthlyUnit_isCorrect() {
        val duration = 30.0
        val watt = 100.0
        val qty = 2.0

        val expectedMonthlyUnit = 6.0

        val actualMonthlyUnit = UserItemCalculationsValidator.calculateMonthlyUnit(duration, watt, qty)

        assertEquals(expectedMonthlyUnit, actualMonthlyUnit, 0.0)
    }

    // Test the calculateMonthlyExpectedUnitAmount function
    @Test
    fun calculateMonthlyExpectedUnitAmount_isCorrect() {
        val munit = 6.0
        val exunit = 100.0
        val unit = 60.0

        val expectedMonthlyExpectedUnit = 10.0

        val actualMonthlyExpectedUnit = UserItemCalculationsValidator.calculateMonthlyExpectedUnitAmount(munit, exunit, unit)

        assertEquals(expectedMonthlyExpectedUnit, actualMonthlyExpectedUnit, 0.0)
    }

    // Test the calculateDailyExpectedUnitAmount function
    @Test
    fun calculateDailyExpectedUnitAmount_isCorrect() {
        val MExUnit = 10.0

        val expectedDailyExpectedUnit = 0.3333333333333333

        val actualDailyExpectedUnit =
            UserItemCalculationsValidator.calculateDailyExpectedUnitAmount(MExUnit)

        assertEquals(expectedDailyExpectedUnit, actualDailyExpectedUnit, 0.0)
    }

    // Test the calculateExpectedDailyHours function
    @Test
    fun calculateExpectedDailyHours_isCorrect() {
        val DExUnit = 5.0
        val watt = 100.0

        val expectedExDailyHours = 50.0

        val actualExDailyHours = UserItemCalculationsValidator.calculateExpectedDailyHours(DExUnit, watt)

        assertEquals(expectedExDailyHours, actualExDailyHours, 0.0)
    }

    // Test the calculateExpectedMonthlyHours function
    @Test
    fun calculateExpectedMonthlyHours_isCorrect() {
        val ExDailyHours = 0.0033333333333333335

        val expectedExMonthlyHours = 0.1

        val actualExMonthlyHours = UserItemCalculationsValidator.calculateExpectedMonthlyHours(ExDailyHours)

        assertEquals(expectedExMonthlyHours, actualExMonthlyHours, 0.0)
    }
}



