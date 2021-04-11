package com.art241111.kcontrolsystem.ui.buttons.arrowButton

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 *  Drawing an arrow.
 *  @author Artem Gerasimov (artem241120@gmail.com)
 */
@Composable
internal fun ArrowShape(
    width: Dp = 50.dp,
    height: Dp = 50.dp,
    color: Color = Color.Red,
    degrees: Float = 0f
) {
    Canvas(
        modifier = Modifier.width(width).height(height),
        onDraw = {
            rotate(degrees = degrees) {
                val heightCenter = height.toPx() / 2f
                val arrowHeight = height.toPx()
                val arrowWidth = width.toPx()

                val triangleHeight = arrowHeight / 3
                val arrowBodyHeight = arrowHeight / 3 * 2
                val arrowLen = arrowWidth / 4

                val arrowPath = Path().apply {
                    // Moves to top center position
                    var xNow = heightCenter
                    var yNow = 0f
                    moveTo(xNow, yNow)

                    // Add line to bottom right triangle
                    xNow += arrowWidth / 2f
                    yNow += triangleHeight
                    lineTo(xNow, yNow)

                    // Add line to arrow body
                    xNow += -arrowLen
                    yNow += 0f
                    lineTo(xNow, yNow)

                    // Add line to lower right corner of the arrow
                    xNow += 0f
                    yNow += arrowBodyHeight
                    lineTo(xNow, yNow)

                    // Add line to lower left corner of the arrow
                    xNow += -(arrowWidth - 2 * arrowLen)
                    yNow += 0f
                    lineTo(xNow, yNow)

                    // Add line to triangle
                    xNow += 0f
                    yNow += -arrowBodyHeight
                    lineTo(xNow, yNow)

                    // Add line to bottom left triangle
                    xNow += -arrowLen
                    yNow += 0f
                    lineTo(xNow, yNow)
                }

                drawPath(
                    color = color,
                    path = arrowPath
                )
            }
        }
    )
}
