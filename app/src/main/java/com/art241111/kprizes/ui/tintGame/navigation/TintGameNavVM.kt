package com.art241111.kprizes.ui.tintGame.navigation

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.art241111.kprizes.navigation.AppState
import com.art241111.kprizes.navigation.MainNavigationVM

/**
 * View model, which is responsible for managing navigation in the tilt app.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
enum class TintGameScreen : AppState {
    TINT_SCREEN, TAKE_SCREEN
}

class TintGameNavVM : ViewModel() {
    private lateinit var mainNavVM: MainNavigationVM
    lateinit var state: State<AppState>

    fun setNavigation(value: MainNavigationVM) {
        mainNavVM = value
        state = mainNavVM.state
    }

    fun moveToTintScreen() {
        mainNavVM.setScreen(TintGameScreen.TINT_SCREEN)
    }

    fun moveToTakeScreen() {
        mainNavVM.setScreen(TintGameScreen.TAKE_SCREEN)
    }

    fun moveToHome() {
        mainNavVM.moveToHome()
    }
}
