package com.example.electricitysaver

import org.junit.Assert.*
import org.junit.Test

class CostCalculatorUnitTest {

    @Test
    fun calELE() {
        val costCalculator = Cost_calculator()
        val days = 30.0
        val units = 15.0
        val (fixedCharge, importCharge, totalCost) = costCalculator.calculateElectricityCost(days, units)
        assertEquals(650.0, fixedCharge, 0.001)
        assertEquals(630.0, importCharge, 0.001)
        assertEquals(1280.0, totalCost, 0.001)
    }
}


//class CalculatorTest {
//    @Test
//    fun testAddition() {
//        val calculator = Calculator()
//        val result: Int = calculator.add(2, 3)
//        Assert.assertEquals(5, result.toLong())
//    }
//}