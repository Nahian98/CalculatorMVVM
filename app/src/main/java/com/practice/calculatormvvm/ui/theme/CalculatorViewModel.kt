package com.practice.calculatormvvm.ui.theme

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
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
            result = evaluateResult(numInput = _numberInputState.value.numberInput)
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

    private  fun evaluateResult(numInput: String): Double {
        if (numInput.isEmpty())
            return 0.0

        var res = numInput[0].toString().toDouble()
        var i = 1

        while(i < numInput.length) {
            val operator = numInput[i]
            val operand = numInput[i + 1]

            if (operand.isDigit()) {
                when (operator) {
                    '+' -> {
                        res += operand.toString().toDouble()
                    }
                    '-' -> {
                        res -= operand.toString().toDouble()
                    }
                    '*' -> {
                        res *= operand.toString().toDouble()
                    }
                    '/' -> {
                        res /= operand.toString().toDouble()
                    }
                }
                i += 2
            } else {
                return 0.0
            }
        }

        return res
    }
}
