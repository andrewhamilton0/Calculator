package com.example.newcalc.feature_calculator.domain

import com.example.newcalc.feature_calculator.domain.CalculationOutcomes

object Calculator {

    fun calculate(s: String): CalculationOutcomes {
        return CalculationOutcomes.Success("44") //TODO
    }

    suspend fun openOrClosedParentheses(s: String): String{
        return ")" //TODO
    }
}