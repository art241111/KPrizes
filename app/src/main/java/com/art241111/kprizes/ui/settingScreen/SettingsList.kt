package com.art241111.kprizes.ui.settingScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.art241111.kcontrolsystem.data.DELAY_SEND_SP
import com.art241111.kcontrolsystem.data.LONG_MOVE_SP
import com.art241111.kcontrolsystem.data.SHORT_MOVE_SP
import com.art241111.kprizes.data.robot.ROBOT_POSITION_ANGLE
import com.art241111.kprizes.data.robot.RobotVM
import com.art241111.kprizes.navigation.SCALE_VISION
import com.art241111.kprizes.repository.ServerVisionVM
import com.art241111.kprizes.ui.settingScreen.addPoints.AddHomePoint
import com.art241111.kprizes.ui.settingScreen.addPoints.AddSetPoint
import com.art241111.kprizes.ui.settingScreen.addPoints.EditPoints
import com.art241111.kprizes.ui.settingScreen.connect.ConnectItem
import com.art241111.saveandloadinformation.sharedPreferences.SharedPreferencesHelperForString

/**
 * List of settings.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@Composable
fun BoxScope.SettingsList(
    modifier: Modifier = Modifier,
    robot: RobotVM,
    back: () -> Unit,
    settingsNavVM: SettingsNavVM,
    sharedPreferences: SharedPreferencesHelperForString,
    moveToAddPoint: (EditPoints) -> Unit,
    serverVision: ServerVisionVM,
) {
    Column(modifier.align(Alignment.Center)) {
        Surface(
            Modifier
                .fillMaxWidth(0.6f)
                .fillMaxHeight(0.6f),
            shape = RoundedCornerShape(10)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(vertical = 20.dp, horizontal = 50.dp)
            ) {
                // Connect to the robot
                item {
                    ConnectItem(
                        itemText = "Подключение к роботу",
                        connectState = robot.connect,
                        onDisconnect = {
                            robot.disconnect()
                        },
                        onConnectClick = { settingsNavVM.moveToConnectToTheRobot() }
                    )
                }

                item {
                    ParamChangeItem(
                        defaultValue = robot.robotPositionAngle,
                        labelText = "Поворот осей X,Y",
                        onValueChange = {
                            robot.robotPositionAngle = it
                            sharedPreferences.save(ROBOT_POSITION_ANGLE, it.toLong().toString())
                        }
                    )
                }

                item {
                    ParamChangeItem(
                        defaultValue = robot.delaySending.toDouble(),
                        labelText = "Задержка отправки сообщений",
                        onValueChange = {
                            robot.delaySending = it.toLong()
                            sharedPreferences.save(DELAY_SEND_SP, it.toLong().toString())
                        }
                    )
                }

                item {
                    ParamChangeItem(
                        defaultValue = robot.defaultButtonDistanceLong,
                        labelText = "Быстрое перемещение",
                        onValueChange = {
                            robot.defaultButtonDistanceLong = it
                            sharedPreferences.save(LONG_MOVE_SP, it.toString())
                        }
                    )
                }

                item {
                    ParamChangeItem(
                        defaultValue = robot.defaultButtonDistanceShort,
                        labelText = "Точечное перемещение",
                        onValueChange = {
                            robot.defaultButtonDistanceLong = it
                            sharedPreferences.save(SHORT_MOVE_SP, it.toString())
                        }
                    )
                }

                item {
                    AddHomePoint(
                        robot = robot,
                        moveToAddPoint = moveToAddPoint
                    )
                }

                item {
                    AddSetPoint(
                        robot = robot,
                        moveToAddPoint = moveToAddPoint
                    )
                }

                // Connect to the robot
                item {
                    ConnectItem(
                        itemText = "Подключение к серверу",
                        connectState = serverVision.connect,
                        onDisconnect = {
                            serverVision.disconnect()
                        },
                        onConnectClick = { settingsNavVM.moveToConnectToTheVisionSever() }
                    )
                }

                item {
                    ParamChangeItem(
                        defaultValue = serverVision.getScale(),
                        labelText = "Масштаб перемещений в режими отслеживания руки",
                        onValueChange = {
                            serverVision.setScale(it)
                            sharedPreferences.save(SCALE_VISION, it.toString())
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(15.dp))

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { back() }
        ) {
            Text(
                text = "Вернуться на главный экран",
                style = MaterialTheme.typography.h2
            )
        }
    }
}
