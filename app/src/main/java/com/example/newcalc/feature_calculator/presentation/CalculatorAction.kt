package com.example.newcalc.feature_calculator.presentation

sealed interface CalculatorAction {
    data class Number(val number: Int): CalculatorAction
    object Clear: CalculatorAction
    object Delete: CalculatorAction
    data class Operation(val operation: CalculatorOperation): CalculatorAction
    object Calculate: CalculatorAction
    object Decimal: CalculatorAction
    object Parentheses: CalculatorAction
}
