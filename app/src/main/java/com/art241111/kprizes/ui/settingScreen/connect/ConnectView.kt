package com.art241111.kprizes.ui.settingScreen.connect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.art241111.kconnectscreen.ui.KConnectScreen
import com.art241111.kprizes.robot.RobotVM

/**
 * Simplified view of KConnectScreen.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

@Composable
fun ConnectView(
    modifier: Modifier = Modifier,
    back: () -> Unit,
    robot: RobotVM
) {
    val defaultIp = remember { mutableStateOf("192.168.31.63") }

    KConnectScreen(
        modifier,
        onBack = { back() },
        onConnect = { ip -> robot.connect(ip) },
        connectStatus = robot.connectStatus,
        onIpChange = { newIp ->
            defaultIp.value = newIp
        },
        defaultIP = defaultIp.value
    )
}
