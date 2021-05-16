package com.art241111.kprizes.ui.settingScreen

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.art241111.kcontrolsystem.data.ControlVM
import com.art241111.kcontrolsystem.data.MoveInTime
import com.art241111.kprizes.data.robot.RobotVM
import com.art241111.kprizes.navigation.MainNavigationVM
import com.art241111.kprizes.repository.ServerVisionVM
import com.art241111.kprizes.ui.settingScreen.addPoints.EditPoints
import com.art241111.kprizes.ui.settingScreen.addPoints.GetPoint
import com.art241111.kprizes.ui.settingScreen.connect.CONST_IP_NAME_ROBOT
import com.art241111.kprizes.ui.settingScreen.connect.CONST_IP_NAME_VISION_SERVER
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
    controlVM: ControlVM,
    moveInTime: MoveInTime,
    serverVision: ServerVisionVM,
    visionGameMode: MutableState<Int>
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
                serverVision = serverVision,
                visionGameMode = visionGameMode
            )
        }

        SettingsScreens.CONNECT_TO_THE_ROBOT -> {
            ConnectView(
                back = { settingsNavVM.back() },
                onConnect = { ip -> robot.connect(ip) },
                connectStatus = robot.connectStatus,
                sharedPreferences = sharedPreferences,
                sharedPreferencesName = CONST_IP_NAME_ROBOT
            )
        }

        SettingsScreens.CONNECT_TO_THE_VISION -> {
            ConnectView(
                back = { settingsNavVM.back() },
                onConnect = { ip -> serverVision.connect(ip) },
                connectStatus = serverVision.connectStatus,
                sharedPreferences = sharedPreferences,
                sharedPreferencesName = CONST_IP_NAME_VISION_SERVER
            )
        }

        SettingsScreens.ADD_POINT -> {
            if (editPoint.value != null) {
                GetPoint(
                    robotVM = robot,
                    controlVM = controlVM,
                    moveInTime = moveInTime,
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
                            EditPoints.FIRST_POINT -> {
                                robot.firstPoint = newPoint
                                sharedPreferences.save(
                                    EditPoints.FIRST_POINT.name, newPoint.toString()
                                )
                            }
                            EditPoints.SECOND_POINT -> {
                                robot.secondPoint = newPoint
                                sharedPreferences.save(
                                    EditPoints.SECOND_POINT.name, newPoint.toString()
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
