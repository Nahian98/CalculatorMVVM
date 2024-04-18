package com.practice.calculatormvvm.model

data class CalculatorState(
    var numberInput: String = "",
    val isError: Boolean = false,
    val errorMessage: String = ""
)
