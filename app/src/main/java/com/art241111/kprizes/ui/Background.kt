package com.art241111.kprizes.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.art241111.kprizes.R

/**
 * Background of the entire app.
 * If you click 3 times on the logo, the settings screen will appear.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@Composable
fun Background(
    modifier: Modifier = Modifier,
    moveSettings: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    val clickCounter = remember { mutableStateOf(0) }
    Box(modifier = modifier) {
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 20.dp, start = 50.dp)
                .size(width = 125.dp, height = 77.dp)
                .clickable {
                    if (++clickCounter.value == 3) {
                        clickCounter.value = 0
                        moveSettings()
                    }
                },
            text = "1C",
            style = MaterialTheme.typography.h1
        )

        content()
    }
}
