package com.example.newcalc.domain

import com.example.newcalc.feature_calculator.domain.CalculationOutcomes
import com.example.newcalc.feature_calculator.domain.Calculator
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CalculatorTest {

    @Test
    fun `Two parentheses and an addition`(){
        val originalString = "3(4+5(6))"
        val actual = Calculator.calculate(originalString)
        val expected = CalculationOutcomes.Success("102")

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `1 parentheses and an addition`(){
        val originalString = "(4+6)+5"
        val actual = Calculator.calculate(originalString)
        val expected = CalculationOutcomes.Success("15")

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Parentheses as multiplication`(){
        val originalString = "(4)5"
        val actual = Calculator.calculate(originalString)
        val expected = CalculationOutcomes.Success("20")

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Addition`(){
        val originalString = "3+3"
        val actual = Calculator.calculate(originalString)
        val expected = CalculationOutcomes.Success("6")

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Addition As Decimal`(){
        val originalString = "3.3+3.3"
        val actual = Calculator.calculate(originalString)
        val expected = CalculationOutcomes.Success("6.6")

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Error`(){
        val originalString = "3+3+++"
        val actual = Calculator.calculate(originalString)
        val expected = CalculationOutcomes.Error

        assertThat(actual).isEqualTo(expected)
    }
}