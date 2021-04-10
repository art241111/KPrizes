package com.art241111.kcontrolsystem.ui.buttons

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
internal fun TextButtons(
    modifier: Modifier = Modifier,
    editName: String,
    value: Double,
    onDecreaseButtonClick: PressOrRelease,
    onZoomButtonClick: PressOrRelease,
    enabled: Boolean = true,
    onTextEnter: (String) -> Unit,
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DecreaseButton(onClick = onDecreaseButtonClick, enabled = enabled)

        val text = remember { mutableStateOf(0.0) }
        val isCorrect = remember { mutableStateOf(false) }

        if (text.value != value && !isCorrect.value) {
            text.value = value
        }

        val focusManager = LocalFocusManager.current

        OutlinedTextField(
            modifier = Modifier.padding(start = 15.dp, end = 15.dp),
            value = text.value.toString(),
            onValueChange = {
                isCorrect.value = true
                text.value = it.toDouble()
            },
            label = { Text(text = editName) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    defaultKeyboardAction(ImeAction.Done)
                    onTextEnter(text.value.toString())
                    isCorrect.value = false

                    focusManager.clearFocus()
                }
            )

        )
        ZoomButton(onClick = onZoomButtonClick, enabled = enabled)
    }
}

@Composable
private fun AdjustmentButton(
    modifier: Modifier = Modifier,
    text: String,
    onPressOrRelease: PressOrRelease,
    shape: Shape,
    enabled: Boolean = true
) {
    // Создаем переменную для сохранения State
    var isPressed by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    // Запоминаем, что нужно делать при нажатии и отпускании
    val pressed = interactionSource.collectIsPressedAsState()
    if (pressed.value) {
        if (!isPressed) {
            onPressOrRelease.onPressed()
        }
    } else {
        if (isPressed) {
            onPressOrRelease.onReleased()
        }

        isPressed = false
    }

    Button(
        modifier = Modifier
            .size(40.dp)
            .then(modifier),
        onClick = { isPressed = true },
        shape = shape,
        interactionSource = interactionSource,
//           backgroundColor = color,
        contentPadding = PaddingValues(0.dp),
        enabled = enabled
    ) {
        Text(
            text = text,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Composable
private fun DecreaseButton(
    modifier: Modifier = Modifier,
    onClick: PressOrRelease,
    enabled: Boolean = true
) {
    val shape = RoundedCornerShape(50)

    AdjustmentButton(modifier, "-", onClick, shape, enabled)
}

@Composable
private fun ZoomButton(
    modifier: Modifier = Modifier,
    onClick: PressOrRelease,
    enabled: Boolean = true
) {
    val shape = RoundedCornerShape(50)

    AdjustmentButton(modifier, "+", onClick, shape, enabled)
}
