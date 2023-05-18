package com.example.newcalc.feature_calculator.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newcalc.R
import com.example.newcalc.UiText
import com.example.newcalc.feature_calculator.domain.CalculationOutcomes
import com.example.newcalc.feature_calculator.domain.Calculator
import kotlinx.coroutines.launch

class CalculatorViewModel: ViewModel() {

    var state by mutableStateOf(
        CalculatorState(
            finalCalculation = UiCalculatorScreenState.NoError(UiText.DynamicString("")),
            previewCalculation = UiCalculatorScreenState.NoError(UiText.DynamicString(""))
        )
    )

    private val currentInput = mutableStateOf("")

    fun onAction(action: CalculatorAction){
        when(action){
            CalculatorAction.Calculate -> calculate()
            CalculatorAction.Clear -> clear()
            CalculatorAction.Decimal -> inputSymbol('.')
            CalculatorAction.Delete -> delete()
            is CalculatorAction.Number -> inputNumber(action.number)
            is CalculatorAction.Operation -> inputSymbol(action.operation.symbol)
            CalculatorAction.Parentheses -> TODO()
        }
    }

    private fun delete() {
        if (currentInput.value.isNotEmpty()) {
            currentInput.value = currentInput.value.substring(0, currentInput.value.length - 1)
            state = CalculatorState(
                finalCalculation = UiCalculatorScreenState.NoError(
                    UiText.DynamicString(
                        getFormattedCurrentInput()
                    )
                ),
                previewCalculation = UiCalculatorScreenState.NoError(
                    UiText.DynamicString(
                        getFormattedCurrentInput()
                    )
                )
            )
        }
    }

    private fun calculate(){
        viewModelScope.launch {
            val s = currentInput.value
            val result = Calculator.calculate(s)
            when(result){
                CalculationOutcomes.Error -> {
                    state = CalculatorState(
                        finalCalculation = UiCalculatorScreenState.Error(
                            UiText.DynamicString(s)
                        ),
                        previewCalculation = UiCalculatorScreenState.Error(
                            UiText.Resource(R.string.format_error)
                        )
                    )
                }
                is CalculationOutcomes.Success -> {
                    currentInput.value = result.string
                    val formattedString = convertArithmeticToOutputString(currentInput.value)
                    state = CalculatorState(
                        finalCalculation = UiCalculatorScreenState.NoError(
                            UiText.DynamicString(formattedString)
                        ),
                        previewCalculation = UiCalculatorScreenState.NoError(
                            UiText.DynamicString("")
                        )
                    )
                }
            }
        }
    }

    private fun convertArithmeticToOutputString(s: String): String{
        val newString = StringBuilder()
        s.forEach { char ->
            when (char){
                '*' -> newString.append('×')
                '/' -> newString.append('÷')
                else -> newString.append(char)
            }
        }
        return newString.toString()
    }

    private fun getFormattedCurrentInput(): String{
        return convertArithmeticToOutputString(currentInput.value)
    }

    private fun inputSymbol(char: Char){
        currentInput.value += char
        state = state.copy(finalCalculation = UiCalculatorScreenState.NoError(UiText.DynamicString(currentInput.value)))
    }

    private fun inputNumber(number: Int) {
        currentInput.value += number
        updatePreview()
        state = state.copy(finalCalculation = UiCalculatorScreenState.NoError(UiText.DynamicString(currentInput.value)))
    }

    private fun updatePreview(){
        viewModelScope.launch {
            val s = currentInput.value
            val result = Calculator.calculate(s)
            when(result){
                is CalculationOutcomes.Success -> {
                    state = state.copy(
                        previewCalculation = UiCalculatorScreenState.NoError(
                            UiText.DynamicString(
                                convertArithmeticToOutputString(result.string)
                            )
                        )
                    )
                }
                else -> Unit
            }
        }

    }

    private fun clear(){
        currentInput.value = ""
        state = CalculatorState(
            finalCalculation = UiCalculatorScreenState.NoError(
                UiText.DynamicString(
                    getFormattedCurrentInput()
                )
            ),
            previewCalculation = UiCalculatorScreenState.NoError(
                UiText.DynamicString(
                    getFormattedCurrentInput()
                )
            )
        )
    }
}

