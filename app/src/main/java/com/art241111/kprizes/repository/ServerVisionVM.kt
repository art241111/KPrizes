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
import com.github.poluka.kControlLibrary.enity.Axes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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

    private var isMoving = false
    private var job: Job = Job()
    private val oldGripperState = mutableStateOf(true)
    fun startMoving(robot: RobotVM, goHome: () -> Unit) {
        moveInTimeDistance.startMoving()
        var isGripperClose = false
        isMoving = true

//        viewModelScope.launch(Dispatchers.IO) {
//
//            movePositionHandler.gripperState.collect { gripper ->
//                Log.d("GRIPPER", gripper.toString())
//                if (oldGripperState.value != gripper){
//                    if (gripper) {
////                        stopMoving()
////
////                        delay(10)
//                        robot dangerousRun CloseGripper()
////                        delay(10)
////                        robot.setMoveMode(false)
////                        delay(10)
////                        goHome()
////                    }
//                    } else {
//                        robot run OpenGripper()
//                    }
//                    oldGripperState.value = gripper
//                }
//
//            }
//        }

        viewModelScope.launch(Dispatchers.IO) {
            if (isMoving) {
                moveDistance.collect { newMoveDistance ->
                    Log.d("Move", newMoveDistance.toString())
                    if (!isMoving) this.cancel()
                    ensureActive()
//                    robot.moveOnArea(
//                        x = newMoveDistance[Axes.Y],
//                        y =  newMoveDistance[Axes.X],
//                        z = newMoveDistance[Axes.Z]
//                    )
                    with(moveInTimeDistance){
                        newPosition = newMoveDistance
                        gripperState = movePositionHandler.gripperState.value
                    }
                }
            }
        }
    }

    fun stopMoving() {
        isMoving = false
        moveInTimeDistance.stopMoving()
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
