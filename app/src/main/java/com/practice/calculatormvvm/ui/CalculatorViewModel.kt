package com.practice.calculatormvvm.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.practice.calculatormvvm.MainActivity
import com.practice.calculatormvvm.model.CalculatorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalculatorViewModel: ViewModel() {

    private val _numberInputState = MutableStateFlow(CalculatorState())
    val numberInputState: StateFlow<CalculatorState> = _numberInputState.asStateFlow()
    var result by mutableDoubleStateOf(0.0)
        private set

    init {
        updateUserInput("")
    }

    fun updateUserInput(input: String) {
        if (input == "DEL") {
            if (_numberInputState.value.numberInput.isNotEmpty()) {
                // Removing the last character for del button onclick
                deleteOnClick()
            }
        } else if (input == "=") {
            result = evaluateResult(expression = _numberInputState.value.numberInput)
        } else {
            _numberInputState.update { currentState->
                currentState.copy(
                    numberInput = _numberInputState.value.numberInput + input
                )
            }
        }

        Log.d("CalculatorViewModel","Updated input: ${_numberInputState.value.numberInput}")
    }

    private fun deleteOnClick() {
        _numberInputState.update { currentState ->
            currentState.copy(
                numberInput = _numberInputState.value.numberInput.substring(0, _numberInputState.value.numberInput.length - 1)
            )
        }
    }

    private fun evaluateResult(expression: String): Double {
        if (expression.isEmpty()) return 0.0

        val tokens = mutableListOf<String>()
        var currentNumber = StringBuilder()

        // Tokenize the input
        for (char in expression) {
            if (char.isDigit() || char == '.') {
                currentNumber.append(char)
            } else if (char in "+-*/") {
                if (currentNumber.isNotEmpty()) {
                    tokens.add(currentNumber.toString())
                    currentNumber = StringBuilder()
                }
                tokens.add(char.toString())
            }
        }
        if (currentNumber.isNotEmpty()) {
            tokens.add(currentNumber.toString())
        }

        // Process multiplication and division first
        val newTokens = mutableListOf<String>()
        var i = 0
        while (i < tokens.size) {
            val token = tokens[i]
            if (token == "*" || token == "/") {
                val left = newTokens.removeLast().toDouble()
                val right = tokens[i + 1].toDouble()
                val result = if (token == "*") left * right else left / right
                newTokens.add(result.toString())
                i += 2 // Skip the next token which is already used
            } else {
                newTokens.add(token)
                i++
            }
        }

        // Process addition and subtraction
        var result = newTokens[0].toDouble()
        i = 1
        while (i < newTokens.size) {
            val operator = newTokens[i]
            val operand = newTokens[i + 1].toDouble()
            when (operator) {
                "+" -> result += operand
                "-" -> result -= operand
            }
            i += 2
        }

        return result
    }
}
