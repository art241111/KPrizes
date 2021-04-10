package com.art241111.kprizes.ui.settingScreen

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.art241111.kprizes.data.robot.RobotVM
import com.art241111.kprizes.navigation.MainNavigationVM
import com.art241111.kprizes.ui.settingScreen.connect.ConnectView
import com.art241111.saveandloadinformation.sharedPreferences.SharedPreferencesHelperForString

/**
 * Settings screen with status navigation.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@Composable
fun BoxScope.SettingsScreen(
    navigate: MainNavigationVM,
    robot: RobotVM,
    sharedPreferences: SharedPreferencesHelperForString
) {
    val settingsNavVM = viewModel<SettingsNavVM>()
    settingsNavVM.setNavigation(navigate)

    when (navigate.state.value) {
        SettingsScreens.HOME -> {
            SettingsList(
                robot = robot,
                settingsNavVM = settingsNavVM,
                sharedPreferences = sharedPreferences,
                back = { navigate.onBackButtonClick() }
            )
        }

        SettingsScreens.CONNECT -> {
            ConnectView(
                back = { settingsNavVM.back() },
                robot = robot,
                sharedPreferences = sharedPreferences
            )
        }
    }
}
