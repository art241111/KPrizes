package com.art241111.kprizes.ui.settingScreen

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.art241111.kcontrolsystem.ui.utils.TiltControl
import com.art241111.kprizes.data.robot.RobotVM
import com.art241111.kprizes.navigation.MainNavigationVM
import com.art241111.kprizes.ui.settingScreen.addPoints.EditPoints
import com.art241111.kprizes.ui.settingScreen.addPoints.GetPoint
import com.art241111.kprizes.ui.settingScreen.connect.CONST_IP_NAME
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
    sharedPreferences: SharedPreferencesHelperForString,
    tiltController: TiltControl
) {
    val settingsNavVM = viewModel<SettingsNavVM>()
    settingsNavVM.setNavigation(navigate)

    val editPoint = remember { mutableStateOf<EditPoints?>(null) }
    when (navigate.state.value) {
        SettingsScreens.HOME -> {
            SettingsList(
                robot = robot,
                settingsNavVM = settingsNavVM,
                sharedPreferences = sharedPreferences,
                back = { navigate.onBackButtonClick() },
                moveToAddPoint = { point ->
                    editPoint.value = point
                    settingsNavVM.moveToAddPoint()
                },
                tiltController = tiltController
            )
        }

        SettingsScreens.CONNECT -> {
            ConnectView(
                back = { settingsNavVM.back() },
                robot = robot,
                sharedPreferences = sharedPreferences
            )
        }

        SettingsScreens.ADD_POINT -> {
            if (editPoint.value != null) {
                GetPoint(
                    robotVM = robot,
                    tiltControl = tiltController,
                    addPoint = { newPoint ->
                        when (editPoint.value) {
                            EditPoints.HOME_POINT -> {
                                robot.homePoint = newPoint
                                sharedPreferences.save(
                                    EditPoints.HOME_POINT.name, newPoint.toString()
                                )
                            }
                            EditPoints.SET_POINT -> {
                                robot.setPoint = newPoint
                                sharedPreferences.save(
                                    EditPoints.SET_POINT.name, newPoint.toString()
                                )
                            }
                        }
                        editPoint.value = null
                        settingsNavVM.back()
                    }
                )
            }
        }
    }
}
