package com.art241111.kprizes.data.robot

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.art241111.kprizes.data.DELAY_SEND_SP
import com.art241111.kprizes.data.LONG_MOVE_SP
import com.art241111.kprizes.data.SHORT_MOVE_SP
import com.art241111.saveandloadinformation.sharedPreferences.SharedPreferencesHelperForString
import com.github.poluka.kControlLibrary.KRobot
import com.github.poluka.kControlLibrary.actions.Command
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Linking the UI to a live robot.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
class RobotVM : ViewModel() {
    var defaultButtonDistanceShort: Double = 10.0
    var defaultButtonDistanceLong: Double = 10.0
    var delaySending: Long = 10L

    private val kRobot = KRobot()
    private val connectRobot = ConnectRobot(kRobot)

    // /**
    //  * Run program on robot
    //  */
    // val isRun: StateFlow<Boolean> = runProgramRobot.isRun
    // fun runProgram(
    //     UICommands: List<UICommand>,
    //     points: MutableMap<String, Point>
    // ) {
    //     runProgramRobot.runProgram(UICommands, points)
    // }

    fun dangerousRun(command: Command) {
        kRobot.dangerousRun(command)
    }

    /**
     * Connecting to the robot.
     */
    val connect: State<Boolean> = connectRobot.connect
    val connectStatus = connectRobot.connectStatus

    fun disconnect() {
        connectRobot.disconnect()
    }

    private var isFirstConnect = true
    fun connect(ip: String, port: Int = 49152) {
        viewModelScope.launch(Dispatchers.IO) {
            connectRobot.connect(ip, port)
        }

        if (isFirstConnect) {
            isFirstConnect = false

            viewModelScope.launch(Dispatchers.IO) {
                connectRobot.startHandlingStatusState()
            }
        }
    }

    override fun onCleared() {
        connectRobot.disconnect()
        super.onCleared()
    }

    /**
     * Robot coordinates.
     */
    val coordinate = kRobot.positionState

    /**
     * Configuring parameters for sending commands.
     */
    fun loadDefaultValue(sharedPreferences: SharedPreferencesHelperForString) {
        delaySending =
            sharedPreferences.load(DELAY_SEND_SP, 70L.toString()).toLong()

        defaultButtonDistanceLong =
            sharedPreferences.load(LONG_MOVE_SP, 10.0.toString()).toDouble()

        defaultButtonDistanceShort =
            sharedPreferences.load(SHORT_MOVE_SP, 1.0.toString()).toDouble()
    }
}
