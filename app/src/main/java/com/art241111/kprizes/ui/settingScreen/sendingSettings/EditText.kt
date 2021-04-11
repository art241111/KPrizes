package com.art241111.kprizes.ui.settingScreen.sendingSettings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/**
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@Composable
internal fun EditText(
    modifier: Modifier = Modifier,
    value: Double,
    labelText: String,
    onValueChange: (Double) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val text = remember { mutableStateOf(value) }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text.value.toString(),
        onValueChange = { newValue ->
            text.value = newValue.toDouble()

            onValueChange(newValue.toDouble())
        },
        label = {
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = labelText,
            )
        },
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                this.defaultKeyboardAction(ImeAction.Done)
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
    )
}
