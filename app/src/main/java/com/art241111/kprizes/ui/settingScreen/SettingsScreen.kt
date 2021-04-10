package com.art241111.kprizes.ui.settingScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        ConnectToTheRobot()
    }
}

@Composable
private fun ConnectToTheRobot(modifier: Modifier = Modifier){
    Row(
        modifier = modifier.fillMaxHeight()
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = "Подключиться к роботу"
        )

        Button(
            onClick = {}
        ) {
            Text(
                text = "Подключиться"
            )
        }
    }
}
