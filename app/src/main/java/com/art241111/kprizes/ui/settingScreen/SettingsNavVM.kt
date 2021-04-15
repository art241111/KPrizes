package com.art241111.kprizes.ui.settingScreen

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.art241111.kprizes.navigation.AppState
import com.art241111.kprizes.navigation.MainNavigationVM

/**
 * View model, which is responsible for managing navigation in the settings screen.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
enum class SettingsScreens : AppState {
    HOME, CONNECT_TO_THE_ROBOT, CONNECT_TO_THE_VISION, ADD_POINT
}

class SettingsNavVM : ViewModel() {
    private lateinit var mainNavVM: MainNavigationVM
    lateinit var state: State<AppState>

    fun setNavigation(value: MainNavigationVM) {
        mainNavVM = value
        state = mainNavVM.state
    }

    fun moveToConnectToTheRobot() {
        mainNavVM.setScreen(SettingsScreens.CONNECT_TO_THE_ROBOT)
    }

    fun moveToConnectToTheVisionSever() {
        mainNavVM.setScreen(SettingsScreens.CONNECT_TO_THE_VISION)
    }

    fun moveToAddPoint() {
        mainNavVM.setScreen(SettingsScreens.ADD_POINT)
    }

    fun back() {
        mainNavVM.onBackButtonClick()
    }
}
