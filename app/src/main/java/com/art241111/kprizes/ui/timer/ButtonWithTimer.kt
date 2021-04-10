package com.art241111.kprizes.ui.timer

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A large button with a rim that acts as a timer.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@Composable
fun BigCircleButton(
    modifier: Modifier = Modifier,
    size: Dp = 680.dp,
    borderColor: Color = MaterialTheme.colors.secondary,
    borderSize: Dp = 20.dp,
    backgroundColor: Color = MaterialTheme.colors.surface,
    progress: Float = 1f,
    onClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
    )

    Surface(
        modifier = modifier.size(size = size),
        color = backgroundColor,
        shape = CircleShape,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() }
        ) {
            CircularProgressIndicator(
                progress = animatedProgress,
                modifier = Modifier
                    .height(size)
                    .width(size),
                color = borderColor,
                strokeWidth = borderSize,
            )

            content()
        }
    }
}
