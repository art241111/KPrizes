package com.art241111.kprizes.repository


import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.art241111.kprizes.data.robot.RobotVM
import com.github.poluka.kControlLibrary.actions.gripper.CloseGripper
import com.github.poluka.kControlLibrary.actions.gripper.OpenGripper
import com.github.poluka.kControlLibrary.enity.position.Point
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

class VisionGame {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job) // Add handlers to Reader




//
//    fun stopGame() {
//        scope.cancel()
//        isMoving.value = false
//    }
}