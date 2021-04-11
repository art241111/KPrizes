package com.art241111.kcontrolsystem.ui.buttons

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.art241111.kcontrolsystem.R
import com.art241111.kcontrolsystem.data.ControlVM
import com.art241111.kcontrolsystem.data.MoveInTime
import com.art241111.kcontrolsystem.data.UIMoveByCoordinate
import com.art241111.kcontrolsystem.ui.theme.TextHeader
import com.art241111.kcontrolsystem.ui.theme.red500
import com.github.poluka.kControlLibrary.enity.position.Point

@Composable
private fun IconButtonWithState(
    modifier: Modifier = Modifier,
    text: String,
    iconId: Int,
    isActive: Boolean = false,
    activeColor: Color = Color.Red,
    defaultColor: Color = Color.Black,
    onClick: () -> Unit
) {
    val color = when (isActive) {
        true -> activeColor
        false -> defaultColor
    }

    Column(
        modifier = modifier
            .clickable { onClick() }
            .clip(RoundedCornerShape(5)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = iconId),
            contentDescription = text,
            tint = color,
        )

        Text(
            text = text,
            color = color
        )
    }
}

@Composable
internal fun ButtonsView(
    modifier: Modifier = Modifier,
    coordinate: State<Point>,
    moveInTime: MoveInTime,
    moveByCoordinate: UIMoveByCoordinate,
    controlVM: ControlVM
) {
    val scrollState: ScrollState = rememberScrollState(0)

    Column(
        modifier = modifier
            .verticalScroll(
                scrollState,
                true,
                reverseScrolling = false
            )
            .fillMaxWidth()
            .then(Modifier.padding(top = 10.dp, bottom = 20.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val state = remember { mutableStateOf(0) }

        Row(
            modifier = Modifier.padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButtonWithState(
                text = stringResource(id = R.string.control_arrows),
                iconId = R.drawable.ic_baseline_joystick_24,
                isActive = state.value == 0,
                onClick = { state.value = 0 },
                activeColor = red500
            )

            Spacer(modifier = Modifier.width(10.dp))
            IconButtonWithState(
                text = stringResource(id = R.string.control_joystick),
                iconId = R.drawable.ic_baseline_joystick_24,
                isActive = state.value == 1,
                onClick = { state.value = 1 },
                activeColor = red500
            )

            Spacer(modifier = Modifier.width(10.dp))
            IconButtonWithState(
                text = stringResource(id = R.string.control_tint),
                iconId = R.drawable.ic_baseline_joystick_24,
                isActive = state.value == 2,
                onClick = { state.value = 2 },
                activeColor = red500
            )
        }

        if (state.value != 2) {
            controlVM.stopTrackingTilt()

            MoveXYZ(
                moveInTime = moveInTime,
                isJoystick = state.value == 1,
            )
        } else {
            controlVM.startTrackingTilt()
            TextHeader(
                text = R.string.control_tint_mode,
                color = red500
            )
        }

        SlowMoving(
            coordinate = coordinate,
            moveInTime = moveInTime,
            moveByCoordinate = moveByCoordinate,
            enabled = state.value != 2
        )
    }
}

@Composable
private fun SlowMoving(
    coordinate: State<Point>,
    moveInTime: MoveInTime,
    moveByCoordinate: UIMoveByCoordinate,
    enabled: Boolean = true
) {
    Spacer(Modifier.height(16.dp))
    TextButtons(
        editName = "X",
        value = coordinate.value[0],
        enabled = enabled,
        onDecreaseButtonClick = PressOrRelease(
            {
                moveInTime[0] = -moveInTime.defaultButtonDistanceShort
            },
            {
                moveInTime[0] = 0.0
            }
        ),
        onZoomButtonClick = PressOrRelease(
            {
                moveInTime[0] = moveInTime.defaultButtonDistanceShort
            },
            {
                moveInTime[0] = 0.0
            }
        )
    ) {
        if (it.isNotEmpty()) {
            moveByCoordinate.moveByX(it.toDouble())
        }
    }

    Spacer(Modifier.height(16.dp))
    TextButtons(
        editName = "Y",
        value = coordinate.value[1],
        enabled = enabled,
        onDecreaseButtonClick = PressOrRelease(
            {
                moveInTime[1] = -moveInTime.defaultButtonDistanceShort
            },
            {
                moveInTime[1] = 0.0
            }
        ),
        onZoomButtonClick = PressOrRelease(
            {
                moveInTime[1] = moveInTime.defaultButtonDistanceShort
            },
            {
                moveInTime[1] = 0.0
            }
        )
    ) {
        if (it.isNotEmpty()) {
            moveByCoordinate.moveByY(it.toDouble())
        }
    }

    Spacer(Modifier.height(16.dp))

    TextButtons(
        editName = "Z",
        value = coordinate.value[2],
        enabled = enabled,
        onDecreaseButtonClick = PressOrRelease(
            {
                moveInTime[2] = -moveInTime.defaultButtonDistanceShort
            },
            {
                moveInTime[2] = 0.0
            }
        ),
        onZoomButtonClick = PressOrRelease(
            {
                moveInTime[2] = moveInTime.defaultButtonDistanceShort
            },
            {
                moveInTime[2] = 0.0
            }
        )
    ) {
        if (it.isNotEmpty()) {
            moveByCoordinate.moveByZ(it.toDouble())
        }
    }
}

internal data class PressOrRelease(
    val onPressed: () -> Unit,
    val onReleased: () -> Unit,
)
