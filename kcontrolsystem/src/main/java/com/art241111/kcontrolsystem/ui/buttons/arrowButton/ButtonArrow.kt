package com.art241111.kcontrolsystem.ui.buttons.arrowButton

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.art241111.kcontrolsystem.ui.theme.red500
import com.art241111.kcontrolsystem.ui.theme.red700

/**
 * Button in the form of an "arrow".
 * @param modifier - The modifier to be applied to the layout.
 * @param width - Arrow width.
 * @param height - Arrow height.
 * @param color - Default arrow color.
 * @param onClickColor - The color of the arrow when you click on it.
 * @param onClick - Action when the button is clicked.
 * @param onPressed - Action when the button is pressed.
 * @param onReleased - Action when the button is released.
 * @param onPressed - Action when the button is pressed.
 * @param degrees - Rotate the arrow at the desired angle. Measured in degrees.
 * @author Artem Gerasimov (artem241120@gmail.com)
 */
@Composable
internal fun ArrowButton(
    modifier: Modifier = Modifier,
    width: Dp = 50.dp,
    height: Dp = 50.dp,
    color: Color = red500,
    onClickColor: Color = red700,
    onClick: () -> Unit = {},
    onPressed: () -> Unit,
    onReleased: () -> Unit,
    degrees: Float = 0f,
) {
    // Создаем переменную для сохранения State
    var isPressed by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    // Запоминаем, что нужно делать при нажатии и отпускании
    val pressed = interactionSource.collectIsPressedAsState()

    val arrowColor =
        if (pressed.value) {
            if (!isPressed) {
                onPressed()
            }
            onClickColor
        } else {
            if (isPressed) {
                onReleased()
            }

            isPressed = false
            color
        }

    // Создаем кнопку
    Box(
        modifier = modifier.then(
            Modifier
                .clickable(interactionSource = interactionSource, indication = null) {
                    isPressed = true
                    onClick()
                }
        )
    ) {
        ArrowShape(
            color = arrowColor,
            width = width,
            height = height,
            degrees = degrees
        )
    }
}
