package com.example.newcalc.feature_calculator.domain

import org.mariuszgromada.math.mxparser.*

object Calculator {

    fun calculate(s: String): CalculationOutcomes {
        val expressionWithAsterisks = addsAsterisksToStringsBetweenParenthesesAndNumbers(s)
        val answerAsDouble = Expression(expressionWithAsterisks).calculate()

        if(answerAsDouble.isNaN()) return CalculationOutcomes.Error
        val answer = formatDouble(answerAsDouble)

        return CalculationOutcomes.Success(answer)
    }

    private fun addsAsterisksToStringsBetweenParenthesesAndNumbers(s: String): String {
        val newString = StringBuilder()
        s.mapIndexed { index, c ->
            try {
                if(c == '(' && s[index-1].isDigit()){
                    newString.append("*")
                }
            } catch (_: Exception){
                Unit
            }
            newString.append(c)
            try {
                if(c == ')' && s[index + 1].isDigit()){
                    newString.append("*")
                }
            } catch (_: Exception) {
                Unit
            }
        }
        return newString.toString()
    }

    private fun formatDouble(number: Double): String {
        val formattedNumber = number.toString()
        return if (formattedNumber.contains('.')) {
            formattedNumber.trimEnd('0').removeSuffix(".")
        } else {
            formattedNumber
        }
    }
}