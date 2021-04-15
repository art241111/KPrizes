package com.art241111.kprizes.ui.settingScreen.connect

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Item in the list to connect to the robot.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@Composable
internal fun ConnectItem(
    modifier: Modifier = Modifier,
    itemText: String,
    connectState: State<Boolean>,
    onDisconnect: () -> Unit,
    onConnectClick: () -> Unit,
) {

    Row(modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            text = itemText
        )

        val text = when (connectState.value) {
            true -> "Отключиться"
            false -> "Подключиться"
        }

        Button(
            onClick = {
                if (!connectState.value) {
                    onConnectClick()
                } else {
                    onDisconnect()
                }
            }
        ) {
            Text(
                text = text,
            )
        }
    }
}
