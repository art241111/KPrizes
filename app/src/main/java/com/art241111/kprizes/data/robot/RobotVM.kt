package com.art241111.kprizes.data.robot

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.art241111.kcontrolsystem.data.DELAY_SEND_SP
import com.art241111.kcontrolsystem.data.LONG_MOVE_SP
import com.art241111.kcontrolsystem.data.SHORT_MOVE_SP
import com.art241111.kprizes.ui.settingScreen.addPoints.EditPoints
import com.art241111.saveandloadinformation.sharedPreferences.SharedPreferencesHelperForString
import com.github.poluka.kControlLibrary.KRobot
import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.dsl.Program
import com.github.poluka.kControlLibrary.enity.position.Point
import com.github.poluka.kControlLibrary.enity.position.toPoint
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

    var homePoint = Point()
    var setPoint = Point()

    private val kRobot = KRobot()
    private val connectRobot = ConnectRobot(kRobot)

    /**
     * Run program on robot
     */

    infix fun run(@ExecutedOnTheRobot program: Program) {
        kRobot.run(program)
    }

    infix fun dangerousRun(command: Command) {
        viewModelScope.launch(Dispatchers.IO) {
            kRobot.dangerousRun(command)
        }
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

        homePoint = sharedPreferences.load(EditPoints.HOME_POINT.name, Point().toString()).toPoint()
        setPoint = sharedPreferences.load(EditPoints.SET_POINT.name, Point().toString()).toPoint()
    }
}
