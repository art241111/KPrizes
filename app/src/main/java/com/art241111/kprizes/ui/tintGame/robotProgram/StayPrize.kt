package com.art241111.kprizes.ui.tintGame.robotProgram

import com.art241111.kprizes.data.robot.RobotVM
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.actions.gripper.closeGripper
import com.github.poluka.kControlLibrary.actions.gripper.openGripper
import com.github.poluka.kControlLibrary.actions.move.moveByAxes
import com.github.poluka.kControlLibrary.actions.move.moveToPoint
import com.github.poluka.kControlLibrary.dsl.kProgram
import com.github.poluka.kControlLibrary.enity.Axes

/**
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
@ExecutedOnTheRobot
internal fun stayPrizes(robot: RobotVM) {
    val distanceToPoint = robot.homePoint[Axes.Z] - robot.setPoint[Axes.Z]

    robot run kProgram {
        closeGripper()
        moveByAxes(Axes.Z, 100.0)
        // departPoint(robot.setPoint, dZ = distanceToPoint)
        moveToPoint(robot.setPoint)
        openGripper()
    }
}
