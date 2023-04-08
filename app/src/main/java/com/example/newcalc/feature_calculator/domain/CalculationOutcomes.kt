package com.example.newcalc.feature_calculator.domain

sealed class CalculationOutcomes(){
    data class Success(val string: String): CalculationOutcomes()
    object Error: CalculationOutcomes()
}
