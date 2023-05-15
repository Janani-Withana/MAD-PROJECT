package com.example.electricitysaver.testing

import android.util.Log

object UserItemCalculationsValidator {
    fun calculateMonthlyUnit(duration: Double, watt: Double, qty: Double): Double {
        var month = duration
        var watt = watt * qty
        return (month * watt) / 1000
    }

    // Define a function to calculate monthly expected unit amount
     fun calculateMonthlyExpectedUnitAmount(munit: Double, exunit: Double, unit: Double): Double {
        return (munit * exunit) / unit
    }

    // Define a function to calculate daily expected unit amount
    fun calculateDailyExpectedUnitAmount(MExUnit: Double): Double {
        return MExUnit / 30
    }

    // Define a function to calculate expected daily hours
    fun calculateExpectedDailyHours(DExUnit: Double, watt: Double): Double {
        return (DExUnit * 1000) / watt
    }

    // Define a function to calculate expected monthly hours
    fun calculateExpectedMonthlyHours(ExDailyHours: Double): Double {
        return ExDailyHours * 30
    }
}