package com.art241111.kprizes.ui.timeUp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * A screen that notifies the user that the time is up.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@Composable
fun TimeUpScreen(
    modifier: Modifier = Modifier,
    moveToHomeScreen: () -> Unit
) {
    Box(
        modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Время вышло",
                style = MaterialTheme.typography.h1
            )

            Button(
                onClick = {
                    moveToHomeScreen()
                }
            ) {
                Text(
                    text = "Вернуться на главный экран",
                )
            }
        }
    }
}
