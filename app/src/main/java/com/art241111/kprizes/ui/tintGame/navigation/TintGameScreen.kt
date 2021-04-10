package com.art241111.kprizes.ui.tintGame.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.art241111.kprizes.ui.timer.TimerVM
import com.art241111.kprizes.ui.tintGame.takeScreen.TakeScreen
import com.art241111.kprizes.ui.tintGame.tintScreen.TintScreen

/**
 * Navigation for the game, using the tilt.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@ExperimentalAnimationApi
@Composable
fun TintGameNavigationScreen(
    navigate: TintGameNavVM,
    timer: TimerVM
    // tiltControl: TiltControl,
) {
    when (navigate.state.value) {
        TintGameScreen.TINT_SCREEN -> {
            TintScreen(
                timer = timer,
                onClick = {
                    navigate.moveToTakeScreen()
                }
            )
        }

        TintGameScreen.TAKE_SCREEN -> {
            timer.stop()

            TakeScreen(
                timer = timer.progress.value.toFloat(),
                onTake = {
                    navigate.moveToHome()
                }
            )
        }
    }
}
