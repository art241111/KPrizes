package com.art241111.kprizes.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.art241111.kcontrolsystem.data.ControlVM
import com.art241111.kcontrolsystem.data.MoveInTime
import com.art241111.kcontrolsystem.ui.utils.TiltControl
import com.art241111.kprizes.data.robot.RobotVM
import com.art241111.kprizes.data.robot.TiltMoveImp
import com.art241111.kprizes.repository.ServerVisionVM
import com.art241111.kprizes.ui.Background
import com.art241111.kprizes.ui.settingScreen.SettingsScreen
import com.art241111.kprizes.ui.settingScreen.SettingsScreens
import com.art241111.kprizes.ui.startScreen.StartScreen
import com.art241111.kprizes.ui.timeUp.TimeUpScreen
import com.art241111.kprizes.ui.timer.TimerVM
import com.art241111.kprizes.ui.tintGame.navigation.TintGameNavVM
import com.art241111.kprizes.ui.tintGame.navigation.TintGameNavigationScreen
import com.art241111.kprizes.ui.tintGame.navigation.TintGameScreen
import com.art241111.kprizes.ui.tintGame.robotProgram.MoveByZVM
import com.art241111.kprizes.ui.tintGame.robotProgram.moveToHome
import com.art241111.kprizes.ui.tintGame.robotProgram.stayPrizes
import com.art241111.kprizes.ui.visionGame.VisionGameScreen
import com.art241111.kprizes.ui.visionGame.VisionGameScreens
import com.art241111.kprizes.utils.LoadDefaultValue
import com.art241111.saveandloadinformation.sharedPreferences.SharedPreferencesHelperForString

/**
 * The main screen, where screens are created depending on the state.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@ExperimentalAnimationApi
@Composable
fun MainNavigateScreen(
    navigate: MainNavigationVM,
    modifier: Modifier = Modifier,
    sharedPreferences: SharedPreferencesHelperForString,
    tiltController: TiltControl,
) {
    // Create Robot
    val robot = viewModel<RobotVM>()
    LoadDefaultValue.load(robot, sharedPreferences)

    val controlVM = viewModel<ControlVM>()
    val moveInTime = MoveInTime(
        delaySending = robot.delaySending,
        defaultButtonDistanceLong = robot.defaultButtonDistanceLong,
        defaultButtonDistanceShort = robot.defaultButtonDistanceShort,
        move = { x, y, z, o, a, t ->
            robot.move(x, y, z, o, a, t)
        }
    )
    controlVM.tiltControl = tiltController
    controlVM.tiltControl.tiltMove = TiltMoveImp(robot)

    // Setting up navigation for the game using tilt
    val tintGameNavVM = viewModel<TintGameNavVM>()
    tintGameNavVM.setNavigation(navigate)

    // Setting up the timer
    val timer = viewModel<TimerVM>()
    val isFirstTimeUp = remember { mutableStateOf(true) }
    if (timer.progress.value <= 0 && isFirstTimeUp.value) {
        isFirstTimeUp.value = false
        navigate.setScreen(GeneralScreen.TIME_UP_SCREEN)
    }

    val serverVision = viewModel<ServerVisionVM>()
    val moveByZVM = viewModel<MoveByZVM>()
    Background(
        modifier = modifier.fillMaxSize(),
        moveSettings = {
            navigate.setScreen(SettingsScreens.HOME)
        },
    ) {
        // Changing the screen depending on the state
        when (navigate.state.value) {
            GeneralScreen.HOME -> {
                serverVision.stopMoving()
                controlVM.stopTrackingTilt()
                moveByZVM.stop()
                timer.stop()

                StartScreen(
                    enabled = robot.connect.value,
                    startGame = {
                        if (robot.connect.value) {
                            timer.resettingProgress()
                            isFirstTimeUp.value = true

                            if (!serverVision.connect.value) {
                                controlVM.startTrackingTilt()
                                tintGameNavVM.moveToTintScreen(timer)
                            } else {
                                navigate.setScreen(VisionGameScreens.MAIN_SCREEN)
                                serverVision.startMoving(robot)
                            }
                        }
                    }
                )
            }

            GeneralScreen.TIME_UP_SCREEN -> {
                TimeUpScreen(
                    moveToHomeScreen = {
                        navigate.moveToHome()
                    }
                )
            }

            is TintGameScreen -> {
                TintGameNavigationScreen(
                    navigate = tintGameNavVM,
                    timer = timer,
                    robot = robot,
                    moveInTime = moveInTime,
                    controlVM = controlVM,
                    serverVisionVM = serverVision,
                    moveByZVM = moveByZVM,
                    onGameEnd = {
                        moveByZVM.stop()
                        stayPrizes(robot)

                        // ?????????????????????? ???????????????????? ??????????
                        moveToHome(robot)
                        navigate.moveToHome()
                    }
                )
            }

            is SettingsScreens -> {
                SettingsScreen(
                    navigate = navigate,
                    robot = robot,
                    serverVision = serverVision,
                    sharedPreferences = sharedPreferences,
                    controlVM = controlVM,
                    moveInTime = moveInTime,
                )
            }

            is VisionGameScreens -> {
                VisionGameScreen(
                    timer = timer,
                    onEndGame = {
                        serverVision.stopMoving()

                        // ?????????????????????? ???????????????????? ??????????
                        moveToHome(robot)
                        navigate.moveToHome()
                    }
                )
            }
        }
    }
}
