package com.art241111.kprizes.ui.settingScreen.connect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.art241111.kconnectscreen.ui.KConnectScreen
import com.art241111.saveandloadinformation.sharedPreferences.SharedPreferencesHelperForString
import com.github.art241111.tcpClient.connection.Status

/**
 * Simplified view of KConnectScreen.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

const val CONST_IP_NAME_ROBOT = "LOAD_IP"
const val CONST_IP_NAME_VISION_SERVER = "LOAD_IP_VISION"

@Composable
fun ConnectView(
    modifier: Modifier = Modifier,
    sharedPreferencesName: String,
    back: () -> Unit,
    onConnect: (String) -> Unit,
    connectStatus: State<Status>,
    sharedPreferences: SharedPreferencesHelperForString
) {
    val defaultIp = remember { sharedPreferences.load(sharedPreferencesName, "192.168.31.63") }

    KConnectScreen(
        modifier,
        onBack = { back() },
        onConnect = { onConnect(it) },
        connectStatus = connectStatus,
        onIpChange = { newIp ->
            sharedPreferences.save(sharedPreferencesName, newIp)
        },
        defaultIP = defaultIp
    )
}
