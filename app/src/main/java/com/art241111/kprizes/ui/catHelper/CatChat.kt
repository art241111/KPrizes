package com.art241111.kprizes.ui.catHelper

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * View to display the text that the cat assistant says.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@Composable
internal fun CatChat(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            content()
        }

        LittleTriangle(
            modifier = Modifier.padding(start = 60.dp)
        )
    }
}

@Composable
private fun LittleTriangle(
    modifier: Modifier = Modifier,
    width: Dp = 40.dp,
    height: Dp = 40.dp,
    color: Color = MaterialTheme.colors.surface,
) {
    Canvas(
        modifier = modifier
            .width(width)
            .height(height),
        onDraw = {
            val arrowPath = Path().apply {
                // Moves to top center position
                var xNow = 0f
                var yNow = 0f
                moveTo(xNow, yNow)

                // Add line to bottom right triangle
                xNow += width.toPx()
                yNow += 0f
                lineTo(xNow, yNow)

                // Add line to triangle
                xNow += -width.toPx()
                yNow += height.toPx()
                lineTo(xNow, yNow)

                // Add line to bottom left triangle
                xNow += -10f
                yNow += -(height.toPx())
                lineTo(xNow, yNow)
            }

            drawPath(
                color = color,
                path = arrowPath
            )
        }
    )
}
