package com.art241111.kprizes.ui.settingScreen.connect

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.art241111.kprizes.robot.RobotVM

/**
 * Item in the list to connect to the robot.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@Composable
internal fun ConnectItem(
    modifier: Modifier = Modifier,
    robot: RobotVM,
    onConnectClick: () -> Unit,
) {

    Row(modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            text = "Подключение к роботу"
        )

        val text = when (robot.connect.value) {
            true -> "Отключиться"
            false -> "Подключиться"
        }

        Button(
            onClick = {
                if (!robot.connect.value) {
                    onConnectClick()
                } else {
                    robot.disconnect()
                }
            }
        ) {
            Text(
                text = text,
            )
        }
    }
}
