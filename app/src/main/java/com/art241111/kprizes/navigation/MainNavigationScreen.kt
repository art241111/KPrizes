package com.art241111.kprizes.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.art241111.kprizes.ui.Background
import com.art241111.kprizes.ui.startScreen.StartScreen
import com.art241111.kprizes.ui.timeUp.TimeUpScreen
import com.art241111.kprizes.ui.timer.TimerVM
import com.art241111.kprizes.ui.tintGame.navigation.TintGameNavVM
import com.art241111.kprizes.ui.tintGame.navigation.TintGameNavigationScreen
import com.art241111.kprizes.ui.tintGame.navigation.TintGameScreen

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
    // sharedPreferences: SharedPreferencesHelperForString,
    // tiltControl: TiltControl,
) {
    // Setting up navigation for the game using tilt
    val tintGameNavVM = viewModel<TintGameNavVM>()
    tintGameNavVM.setNavigation(navigate)

    // Setting up the timer
    val timer = viewModel<TimerVM>()
    val isFirstTimeUp = remember { mutableStateOf(true) }
    if (timer.progress.value <= 0 && isFirstTimeUp.value) {
        isFirstTimeUp.value = false
        // navigate.moveToHome()
        navigate.setScreen(GeneralScreen.TIME_UP_SCREEN)
    }

    Background(
        modifier = modifier.fillMaxSize()
    ) {
        // Changing the screen depending on the state
        when (navigate.state.value) {
            GeneralScreen.HOME -> {
                StartScreen(
                    startGame = {
                        timer.resettingProgress()
                        isFirstTimeUp.value = true

                        // TODO: В зависимотси от настроек
                        tintGameNavVM.moveToTintScreen()
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
                timer.start(60000L)
                TintGameNavigationScreen(
                    navigate = tintGameNavVM,
                    timer = timer
                )
            }
        }
    }
}
