package com.art241111.kprizes.ui.settingScreen

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.art241111.kprizes.navigation.MainNavigationVM
import com.art241111.kprizes.robot.RobotVM
import com.art241111.kprizes.ui.settingScreen.connect.ConnectView

/**
 * Settings screen with status navigation.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@Composable
fun BoxScope.SettingsScreen(
    navigate: MainNavigationVM,
    robot: RobotVM
) {
    val settingsNavVM = viewModel<SettingsNavVM>()
    settingsNavVM.setNavigation(navigate)

    when (navigate.state.value) {
        SettingsScreens.HOME -> {
            SettingsList(
                robot = robot,
                settingsNavVM = settingsNavVM
            )
        }

        SettingsScreens.CONNECT -> {
            ConnectView(
                back = { settingsNavVM.back() },
                robot = robot
            )
        }
    }
}
