package com.example.newcalc.feature_calculator.presentation

sealed class CalculatorOperation(val symbol: Char){
    object Add: CalculatorOperation('+')
    object Subtract: CalculatorOperation('-')
    object Multiply: CalculatorOperation('x')
    object Divide: CalculatorOperation('/')
}
