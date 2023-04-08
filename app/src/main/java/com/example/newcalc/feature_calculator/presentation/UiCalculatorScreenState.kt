package com.example.newcalc.feature_calculator.presentation

import com.example.newcalc.UiText

sealed class UiCalculatorScreenState(open val uiText: UiText) {
    data class NoError(override val uiText: UiText): UiCalculatorScreenState(uiText = uiText)
    data class Error(override val uiText: UiText): UiCalculatorScreenState(uiText = uiText)
}