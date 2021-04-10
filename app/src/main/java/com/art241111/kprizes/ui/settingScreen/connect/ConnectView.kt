package com.art241111.kprizes.ui.settingScreen.connect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.art241111.kconnectscreen.ui.KConnectScreen
import com.art241111.kprizes.data.robot.RobotVM
import com.art241111.saveandloadinformation.sharedPreferences.SharedPreferencesHelperForString

/**
 * Simplified view of KConnectScreen.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

const val CONST_IP_NAME = "LOAD_IP"

@Composable
fun ConnectView(
    modifier: Modifier = Modifier,
    back: () -> Unit,
    robot: RobotVM,
    sharedPreferences: SharedPreferencesHelperForString
) {
    val defaultIp = remember { sharedPreferences.load(CONST_IP_NAME, "192.168.31.63") }

    KConnectScreen(
        modifier,
        onBack = { back() },
        onConnect = { ip -> robot.connect(ip) },
        connectStatus = robot.connectStatus,
        onIpChange = { newIp ->
            sharedPreferences.save(CONST_IP_NAME, newIp)
        },
        defaultIP = defaultIp
    )
}
