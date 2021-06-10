package com.art241111.kprizes.repository

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.art241111.kprizes.data.robot.RobotVM
import com.github.art241111.tcpClient.Client
import com.github.art241111.tcpClient.connection.Status
import com.github.poluka.kControlLibrary.actions.gripper.CloseGripper
import com.github.poluka.kControlLibrary.actions.gripper.OpenGripper
import com.github.poluka.kControlLibrary.actions.move.MoveToPoint
import com.github.poluka.kControlLibrary.enity.position.Point
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

/**
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class ServerVisionVM : ViewModel() {
    lateinit var moveInTimeDistance: MoveInTimeDistance

    private val client = Client()
    private val movePositionHandler = MovePositionHandler(client.incomingText)

    private val _connect = mutableStateOf(false)
    val connect: State<Boolean> = _connect

    private var _connectStatus = mutableStateOf(Status.DISCONNECTED)
    var connectStatus: State<Status> = _connectStatus

    private val moveDistance = movePositionHandler.moveDistance

    fun setScale(scale: Double) {
        movePositionHandler.scale = scale
    }

    fun getScale(): Double = movePositionHandler.scale

    private var isMoving = mutableStateOf(false)
    private var job: Job = Job()

    //    private var scope = viewModelScope
    private var visionGame = VisionGame()

    /**
     * @param mode - тип игры.
     * Если 0 - то пользователь переносит игрушку самостоятельно.
     * Если 1 - то робот перемещает игрушку, когда пользователь захватил ее.
     */
    private var _robot = RobotVM()
    fun startGame(mode: State<Int>, robot: RobotVM, stayPrize: () -> Unit, goHome: () -> Unit) {
//        if (isGameStart) {
        this._robot = robot

        if (!isMoving.value) {
            isMoving.value = true
            Log.d("GAME_START", "isGameStart.toString()")

            startMoving(
                moveInTimeDistance = moveInTimeDistance,
                moveDistance = movePositionHandler.moveDistance,
                gripper = movePositionHandler.gripperState
            )
        }
//            else {
//                stopMoving()
//                delay(1000L)
//                robot dangerousRun MoveToPoint(robot.homePoint)
//            }
//        }

//        viewModelScope.launch() {
//            movePositionHandler.isGameStart.collect { isGameStart ->
//                Log.d("GAME_START", isGameStart.toString())
//                ensureActive()
//                if (isGameStart) {
//                    if (!isMoving.value) {
//                        isMoving.value = true
//                        Log.d("GAME_START", "isGameStart.toString()")
//
//                        startMoving(
//                            moveInTimeDistance = moveInTimeDistance,
//                            moveDistance = movePositionHandler.moveDistance,
//                            gripper = movePositionHandler.gripperState
//                        )
//                    } else {
//                        stopMoving()
//                        delay(1000L)
//                        robot dangerousRun MoveToPoint(robot.homePoint)
//                    }
//                }


//                    startHandleGripperState(
//                        mode = mode,
//                        robot = robot,
//                        stayPrize = stayPrize,
//                        goHome = goHome,
//                        gripperState = movePositionHandler.gripperState,
//                        stopMoving = { stopMoving() }
//                    )
    }
//        }
//    }


    private val oldGripperState = mutableStateOf(true)

    //    private val isMoving = mutableStateOf(false)
    fun startHandleGripperState(
        mode: State<Int>,
        gripperState: StateFlow<Boolean>,
        robot: RobotVM,
        stayPrize: () -> Unit,
        goHome: () -> Unit,
        stopMoving: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            gripperState.collect { gripper ->
                if (!isMoving.value) this.cancel()
                ensureActive()

                Log.d("GRIPPER", gripper.toString())
                if (oldGripperState.value != gripper) {
                    oldGripperState.value = gripper

                    if (gripper) {
                        robot dangerousRun CloseGripper()
                    } else {

                        robot run OpenGripper()

                    }
                }
////
////                    when (mode.value) {
////                        0 -> {
////                            if (gripper) {
////                                robot dangerousRun CloseGripper()
////                            } else {
////                                stopMoving()
////                                delay(1000)
////
////                                robot run OpenGripper()
////
////                                delay(20)
////                                goHome()
////                            }
////                        }
////
////                        1 -> {
////                            if (gripper) {
////                                stopMoving()
////
////                                robot dangerousRun CloseGripper()
////                                delay(200)
////                                robot dangerousRun CloseGripper()
////                                delay(200)
////                                stayPrize()
////                                goHome()
////                            } else {
////                                robot run OpenGripper()
////                            }
////                        }
////                    }
//            }
//
            }
        }
    }

    fun startMoving(
        moveInTimeDistance: MoveInTimeDistance,
        moveDistance: StateFlow<Point>,
        gripper: StateFlow<Boolean>,
    ) {
        moveInTimeDistance.startMoving()

        viewModelScope.launch(Dispatchers.IO) {
            moveDistance.collect { newMoveDistance ->
                Log.d("Move", newMoveDistance.toString())
                if (!isMoving.value) this.cancel()
                ensureActive()

                with(moveInTimeDistance) {
                    newPosition = newMoveDistance
                    gripperState = gripper.value
                }
            }
        }
    }

    //
    fun stopMoving() {
//        scope.cancel()
        isMoving.value = false
        moveInTimeDistance.stopMoving()

        viewModelScope.launch {
            delay(1000L)
            _robot dangerousRun MoveToPoint(_robot.homePoint)

        }
    }

    private var isHandling = false
    fun connect(address: String, port: Int = 9999) {
        if (!isHandling) {
            viewModelScope.launch(Dispatchers.IO) {
                movePositionHandler.handlePosition()
            }

            viewModelScope.launch(Dispatchers.IO) {
                startHandlingStatusState()
            }
        }

        isHandling = true

        viewModelScope.launch(Dispatchers.IO) {
            client.connect(address, port)
        }
    }

    private suspend fun startHandlingStatusState() {
        client.statusState.collect { status ->
            _connect.value = status == Status.COMPLETED

            if (status != null) {
                _connectStatus.value = status
            }
            Log.d("Status_VISION_SERVER", status.toString())
        }
    }

    fun disconnect() {
        // isHandling = false
        movePositionHandler.stopHandlePosition()

        client.disconnect()
    }
}

