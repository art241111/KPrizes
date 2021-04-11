package com.art241111.kprizes.ui.tintGame.robotProgram

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.art241111.kcontrolsystem.data.MoveInTime
import com.art241111.kprizes.data.robot.RobotVM
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.enity.Axes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class MoveByZVM() : ViewModel() {
    private var isFirst = true
    private var isWork = false

    private lateinit var moveInTime: MoveInTime
    fun setMoveInTime(moveInTime: MoveInTime) {
        this.moveInTime = moveInTime
        moveInTime[0] = 0.0
        moveInTime[1] = 0.0
    }

    @ExecutedOnTheRobot
    internal fun move(robot: RobotVM) {
        if (isFirst) {
            isFirst = false
            isWork = true

            viewModelScope.launch(Dispatchers.IO) {
                while ((robot.coordinate.value[Axes.Z] > -120) && (isWork)) {
                    moveInTime[2] = -10.0
                    delay(100L)
                }
            }
        }
    }

    internal fun stop() {
        isWork = false

        moveInTime[0] = 0.0
        moveInTime[1] = 0.0
        moveInTime[2] = 0.0
    }
}
