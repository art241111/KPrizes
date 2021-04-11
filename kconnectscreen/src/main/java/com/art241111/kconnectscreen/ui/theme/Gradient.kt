package com.art241111.kconnectscreen.ui.theme

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

fun Modifier.verticalGradientBackground() = drawWithCache {
    val colorStart = Color(247, 98, 93)
    val colorEnd = Color(249, 128, 108)

    val gradient = Brush.horizontalGradient(listOf(colorStart, colorEnd), 0.0f, size.width, TileMode.Clamp)
    onDrawBehind {
        drawRect(brush = gradient)
    }
}
