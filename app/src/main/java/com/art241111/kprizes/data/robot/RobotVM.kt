package com.art241111.kprizes.data.robot

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.poluka.kControlLibrary.KRobot
import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.actions.move.MoveNew
import com.github.poluka.kControlLibrary.actions.move.MoveOnDistance
import com.github.poluka.kControlLibrary.actions.points.SetMaxPoint
import com.github.poluka.kControlLibrary.actions.points.SetMiddlePoint
import com.github.poluka.kControlLibrary.actions.points.SetMinPoint
import com.github.poluka.kControlLibrary.actions.service.mode.ModeIsInArea
import com.github.poluka.kControlLibrary.actions.service.mode.MoveMode
import com.github.poluka.kControlLibrary.dsl.Program
import com.github.poluka.kControlLibrary.enity.Axes
import com.github.poluka.kControlLibrary.enity.Distance
import com.github.poluka.kControlLibrary.enity.position.Point
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * Linking the UI to a live robot.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
const val ROBOT_POSITION_ANGLE = "ROBOT_POSITION_ANGLE "

class RobotVM : ViewModel() {
    var defaultButtonDistanceShort: Double = 10.0
    var defaultButtonDistanceLong: Double = 10.0
    var delaySending: MutableState<Long> = mutableStateOf(10L)
    var robotPositionAngle = 0.0

    private val _isInArea = mutableStateOf(false)
    val isInArea: State<Boolean> = _isInArea

    fun changeIsAreaStatus() {
        val status = !_isInArea.value
        _isInArea.value = status
        viewModelScope.launch(Dispatchers.IO) {
            kRobot run ModeIsInArea(status)
            delay(10L)
            if (status) {
                kRobot run SetMaxPoint(maxPoint)
                kRobot run SetMinPoint(minPoint)
            }
        }
    }

    var homePoint = Point(-450, 70, -150, -115,180,50)
    var setPoint = Point()

    var firstPoint = Point()
        set(value) {
            field = value

            setMinAndMaxPoint()
        }

    var secondPoint = Point()
        set(value) {
            field = value

            setMinAndMaxPoint()
        }

    private val minPoint = Point()
    private val maxPoint = Point()

    private val middlePoint = Point()
        get() {
            for (i in 0..2) {
                field[i] = (maxPoint[i] + minPoint[i]) / 2
                if (minPoint[i] > maxPoint[i]) field[i] = -field[i]
            }

            return field
        }

    private fun setMinAndMaxPoint() {
        for (i in 0..3) {
            if (firstPoint[i] > secondPoint[i]) {
                maxPoint[i] = firstPoint[i]
                minPoint[i] = secondPoint[i]
            } else {
                maxPoint[i] = secondPoint[i]
                minPoint[i] = firstPoint[i]
            }
        }

        val robot = this
        viewModelScope.launch {
            robot dangerousRun SetMaxPoint(maxPoint)
            delay(20L)
            robot dangerousRun SetMinPoint(minPoint)
            delay(20L)
            robot dangerousRun SetMiddlePoint(middlePoint)
        }
    }

    private suspend fun sendPoints() {
        this dangerousRun SetMinPoint(minPoint)
        delay(30L)
        this dangerousRun SetMaxPoint(maxPoint)
        delay(30L)
        this dangerousRun SetMiddlePoint(middlePoint)
    }

    private val kRobot = KRobot()
    private val connectRobot = ConnectRobot(kRobot)

    /**
     * Run program on robot
     */

    infix fun run(@ExecutedOnTheRobot program: Program) {
        kRobot.run(program)
    }

    infix fun run(@ExecutedOnTheRobot command: Command) {
        kRobot.run(command)
    }

    infix fun dangerousRun(command: Command) {
        viewModelScope.launch(Dispatchers.IO) {
            kRobot.dangerousRun(command)
        }
    }

    infix fun move(distance: Distance) {
        this dangerousRun MoveOnDistance(distance)
    }

    fun moveOnArea(
        x: Double = 0.0,
        y: Double = 0.0,
        z: Double = 0.0,
        gripperState: Boolean
    ) {
        val area = maxPoint - minPoint

        Log.d("AREA", middlePoint.toString())

//        if ((abs(x) != 0.0) and (abs(y) != 0.0) and (abs(z) != 0.0)) {
//            this dangerousRun MoveNew(
//                x = middlePoint[Axes.X] + (x / 2) * area[Axes.X],
//                y = middlePoint[Axes.Y] + (y / 2) * area[Axes.Y],
//                z = middlePoint[Axes.Z] + (z / 2) * area[Axes.Z],
//                gripperState = gripperState
//            )
//        }

        val newX = if((x >= -1) and (x <= 1)) x else if (x < -1) -1.0 else 1.0
        val newY = if((y >= -1) and (y <= 1)) y else if (y < -1) -1.0 else 1.0
        val newZ = if((z >= -1) and (z <= 1)) z else if (z < -1) -1.0 else 1.0

        if ((abs(newX) != 0.0) and (abs(newY) != 0.0) and (abs(newZ) != 0.0)) {
            this dangerousRun MoveNew(
                x = (newX / 2) * area[Axes.X],
                y = (newY / 2) * area[Axes.Y],
                z = (newZ / 2) * area[Axes.Z],
                gripperState = gripperState
            )
        }
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

    fun setMoveMode(value: Boolean) {
        dangerousRun(MoveMode(value))
    }
}
