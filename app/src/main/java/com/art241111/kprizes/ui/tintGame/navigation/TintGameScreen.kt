package com.art241111.kprizes.ui.tintGame.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.art241111.kcontrolsystem.data.ControlVM
import com.art241111.kcontrolsystem.data.MoveInTime
import com.art241111.kprizes.data.robot.RobotVM
import com.art241111.kprizes.ui.timer.TimerVM
import com.art241111.kprizes.ui.tintGame.robotProgram.MoveByZVM
import com.art241111.kprizes.ui.tintGame.robotProgram.stayPrizes
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
    timer: TimerVM,
    robot: RobotVM,
    moveInTime: MoveInTime,
    controlVM: ControlVM
) {
    val moveByZVM = viewModel<MoveByZVM>()

    when (navigate.state.value) {
        TintGameScreen.TINT_SCREEN -> {

            TintScreen(
                timer = timer,
                onClick = {
                    timer.stop()

                    moveByZVM.setMoveInTime(moveInTime)
                    controlVM.stopTrackingTilt()

                    navigate.moveToTakeScreen()
                }
            )
        }

        TintGameScreen.TAKE_SCREEN -> {
            moveByZVM.move(robot)

            TakeScreen(
                timer = timer.progress.value.toFloat(),
                onTake = {
                    moveByZVM.stop()
                    stayPrizes(robot)

                    // Перемещение надомашний экран
                    navigate.moveToHome()
                }
            )
        }
    }
}
