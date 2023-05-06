package com.example.electricitysaver

import com.example.electricitysaver.testing.CostCalculatorValidator
import org.junit.Assert.assertEquals
import org.junit.Test

class CostCalculatorValidatorTest {

    @Test
    fun `test calculateElectricityCost with 0 days`() {

        val expected = Triple(2000.0, 0.0, 0.0)
        val result = CostCalculatorValidator.calculateElectricityCost(0.0, 100.0)
        assertEquals(expected, result)
    }

    @Test
    fun `test calculateElectricityCost with 23 days and les than 60 units`() {

        val expected = Triple(650.0, 2352.0, 3002.0)
        val result = CostCalculatorValidator.calculateElectricityCost(23.0, 56.0)
        assertEquals(expected, result)
    }

    @Test
    fun `test calculateElectricityCost with less than 54 days`() {
        val expected = Triple(1500.0, 4328.0, 5828.0)
        val result = CostCalculatorValidator.calculateElectricityCost(28.0, 100.0)
        assertEquals(expected, result)
    }

    @Test
    fun `test calculateElectricityCost with 54 days`() {
        val expected = Triple(2700.0, 13704.0, 16404.0)
        val result = CostCalculatorValidator.calculateElectricityCost(54.0, 300.0)
        assertEquals(expected, result)
    }

    @Test
    fun `test calculateElectricityCost with more than 54 days`() {
        val expected = Triple(4500.0, 12840.0, 17340.0)
        val result = CostCalculatorValidator.calculateElectricityCost(90.0, 300.0)
        assertEquals(expected, result)
    }

    @Test
    fun `test calculateElectricityCost with more than 120 days`() {
        val expected = Triple(2000.0, 28800.0, 30800.0)
        val result = CostCalculatorValidator.calculateElectricityCost(50.0, 500.0)
        assertEquals(expected, result)
    }

    @Test
    fun `test calculateElectricityCost with more than 1000 units in 45 days`() {
        val expected = Triple(2000.0, 67170.0, 69170.0)
        val result = CostCalculatorValidator.calculateElectricityCost(45.0, 1000.0)
        assertEquals(expected, result)
    }
}
