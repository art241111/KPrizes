package com.art241111.kprizes.data.robot

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.poluka.kControlLibrary.KRobot
import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.actions.move.MoveOnDistance
import com.github.poluka.kControlLibrary.dsl.Program
import com.github.poluka.kControlLibrary.enity.Distance
import com.github.poluka.kControlLibrary.enity.position.Point
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Linking the UI to a live robot.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
const val ROBOT_POSITION_ANGLE = "ROBOT_POSITION_ANGLE "

class RobotVM : ViewModel() {
    var defaultButtonDistanceShort: Double = 10.0
    var defaultButtonDistanceLong: Double = 10.0
    var delaySending: Long = 10L
    var robotPositionAngle = 0.0

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

    infix fun move(distance: Distance) {
        this dangerousRun MoveOnDistance(distance)
    }

    fun move(
        x: Double = 0.0,
        y: Double = 0.0,
        z: Double = 0.0,
        o: Double = 0.0,
        a: Double = 0.0,
        t: Double = 0.0
    ) {
        var newX = 0.0
        var newY = 0.0

        when (robotPositionAngle) {
            0.0 -> {
                newX = x
                newY = y
            }
            90.0 -> {
                newX = y
                newY = x
            }
            180.0 -> {
                newX = -x
                newY = -y
            }
            270.0 -> {
                newX = -y
                newY = x
            }
        }

        this dangerousRun MoveOnDistance(newX, newY, z, o, a, t)
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
}
