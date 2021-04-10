package com.art241111.kprizes.ui.settingScreen

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.art241111.kprizes.robot.RobotVM
import com.art241111.kprizes.ui.settingScreen.connect.ConnectItem

/**
 * List of settings.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@Composable
fun BoxScope.SettingsList(
    modifier: Modifier = Modifier,
    robot: RobotVM,
    settingsNavVM: SettingsNavVM
) {
    Surface(
        modifier
            .fillMaxWidth(0.6f)
            .fillMaxHeight(0.6f)
            .align(Alignment.Center),
        shape = RoundedCornerShape(10)
    ) {
        LazyColumn() {
            // Connect to the robot
            item {
                ConnectItem(
                    modifier = Modifier.padding(vertical = 20.dp, horizontal = 50.dp),
                    robot = robot,
                    onConnectClick = { settingsNavVM.moveToConnect() }
                )
            }
        }
    }
}
