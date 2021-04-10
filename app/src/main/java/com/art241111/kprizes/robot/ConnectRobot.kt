package com.art241111.kprizes.robot

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.github.art241111.tcpClient.connection.Status
import com.github.poluka.kControlLibrary.KRobot
import kotlinx.coroutines.flow.collect

/**
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class ConnectRobot(private val robot: KRobot) {
    private val _connect = mutableStateOf(false)
    val connect: State<Boolean> = _connect

    private var _connectStatus = mutableStateOf(Status.DISCONNECTED)
    var connectStatus: State<Status> = _connectStatus

    /**
     * Connecting to the robot
     */
    fun connect(
        ip: String,
        port: Int = 49152,
    ) {
        if (_connectStatus.value != Status.COMPLETED && _connectStatus.value != Status.CONNECTING) {
            robot.connect(ip, port)
        }
    }

    suspend fun startHandlingStatusState() {
        robot.statusState.collect { status ->
            _connect.value = status == Status.COMPLETED

            if (status != null) {
                _connectStatus.value = status
            }
            Log.d("Status", status.toString())
        }
    }

    fun disconnect() {
        robot.disconnect()
    }
}
