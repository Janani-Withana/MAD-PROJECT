package com.example.electricitysaver.testing

object CostCalculatorValidator {

    fun calculateElectricityCost(days: Double, units: Double): Triple<Double, Double, Double> {
        val fixedRate: Double = if (units / (days / 30.0) in 0.0..60.0) {
            650.0
        } else if (units / (days / 30.0) in 61.0..90.0) {
            650.0
        } else if (units / (days / 30.0) in 91.0..120.0) {
            1500.0
        } else if (units / (days / 30.0) in 121.0..180.0) {
            1500.0
        } else {
            2000.0
        }

        val fixedCharge: Double = if (days < 54.0) {
            fixedRate
        } else {
            fixedRate * (days / 30.0)
        }

        if (days == 0.0) {
            return Triple(fixedCharge, 0.0, 0.0)
        }

        val block1Rate: Double = 42.00
        val block2Rate: Double = 42.00
        val block3Rate: Double = 50.00
        val block4Rate: Double = 50.00
        val block5Rate: Double = 75.00

        val block1Units: Double = days * 2.0
        val block2Units: Double = days
        val block3Units: Double = days
        val block4Units: Double = days * 2.0
        val block5Units: Double = 0.0

        var remainingUnits: Double = units
        var cost: Double = 0.0

        if(units <= 60){
            // Calculate cost for block 1
            val block1: Double = minOf(block1Units, remainingUnits)
            remainingUnits -= block1
            cost += block1 * block1Rate

            // Calculate cost for block 2
            val block2: Double = minOf(block2Units, remainingUnits)
            remainingUnits -= block2
            cost += block2 * block2Rate

            // Add fixed charge
            cost += fixedCharge
        }else {
            // Calculate cost for block 1
            val block1: Double = minOf(block1Units, remainingUnits)
            remainingUnits -= block1
            cost += block1 * block1Rate

            // Calculate cost for block 2
            val block2: Double = minOf(block2Units, remainingUnits)
            remainingUnits -= block2
            cost += block2 * block2Rate

            // Calculate cost for block 3
            val block3: Double = minOf(block3Units, remainingUnits)
            remainingUnits -= block3
            cost += block3 * block3Rate

            // Calculate cost for block 4
            val block4: Double = minOf(block4Units, remainingUnits)
            remainingUnits -= block4
            cost += block4 * block4Rate

            // Calculate cost for block 5
            val block5: Double = remainingUnits
            cost += block5 * block5Rate

            // Add fixed charge
            cost += fixedCharge
        }

        var importCharge = if(days == 0.0){
            fixedCharge
        }else{
            cost - fixedCharge
        }

        return Triple(fixedCharge, importCharge, cost)
    }
}