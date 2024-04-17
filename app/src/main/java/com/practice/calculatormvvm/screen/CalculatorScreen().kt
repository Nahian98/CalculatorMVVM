package com.practice.calculatormvvm.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.practice.calculatormvvm.MainActivity
import com.practice.calculatormvvm.ui.theme.CalculatorViewModel

@Composable
fun CalculatorContainer(calculatorViewModel: CalculatorViewModel = viewModel()) {
    val calculatorInputState by calculatorViewModel.numberInputState.collectAsState()
    Column {
        NumberInputField(
            value = calculatorInputState.numberInput,
        )

        NumberOutputField(value = calculatorViewModel.result.toString())

        ButtonRows(btn1 = "sin", btn2 = "cos", btn3 = "tan", btn4 = "-", onButtonClick = { newValue ->
            calculatorViewModel.updateUserInput(newValue)
        })
        ButtonRows(btn1 = "7", btn2 = "8", btn3 = "9", btn4 = "/", onButtonClick = { newValue ->
            calculatorViewModel.updateUserInput(newValue)
        })
        ButtonRows(btn1 = "4", btn2 = "5", btn3 = "6", btn4 = "*", onButtonClick = { newValue ->
            calculatorViewModel.updateUserInput(newValue)
        })
        ButtonRows(btn1 = "1", btn2 = "2", btn3 = "3", btn4 = "+", onButtonClick = { newValue ->
            calculatorViewModel.updateUserInput(newValue)
        })
        ButtonRows(btn1 = ".", btn2 = "0", btn3 = "DEL", btn4 = "=", onButtonClick = { newValue ->
            calculatorViewModel.updateUserInput(newValue)
        })
    }
    Log.d(MainActivity.TAG, "Input: ${calculatorInputState.numberInput}")
}

@Composable
fun NumberInputField(
    value: String,
) {
//    TextField(
//        value = value,
//        modifier = modifier,
//        onValueChange = onValueChanged,
//        label = { Text(stringResource(label)) },
//        keyboardOptions = keyboardOption
//    )
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Blue),
        text = value,
        color = Color.White,
        textAlign = TextAlign.End
    )
}

@Composable
fun NumberOutputField(
    value: String
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Green),
        text = value,
    )
}

@Composable
fun CalculatorButton(
    value: String,
    onButtonClick: (String) -> Unit
) {
    Button(onClick = {
        onButtonClick(value)
    }) {
        Text(text = value)
    }
}

@Composable
fun ButtonRows(
    btn1: String,
    btn2: String,
    btn3: String,
    btn4: String,
    onButtonClick: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CalculatorButton(value = btn1, onButtonClick = onButtonClick)
        CalculatorButton(value = btn2, onButtonClick = onButtonClick)
        CalculatorButton(value = btn3, onButtonClick = onButtonClick)
        CalculatorButton(value = btn4, onButtonClick = onButtonClick)
    }
}

@Preview
@Composable
private fun CalculatorContainerPreview() {
    CalculatorContainer()
}