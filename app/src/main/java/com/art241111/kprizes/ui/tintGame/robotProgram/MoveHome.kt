package com.art241111.kprizes.ui.tintGame.robotProgram

import com.art241111.kprizes.data.robot.RobotVM
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.actions.move.moveToPoint
import com.github.poluka.kControlLibrary.dsl.kProgram

/**
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
@ExecutedOnTheRobot
fun moveToHome(robot: RobotVM) {
    robot run kProgram {
        moveToPoint(robot.homePoint)
    }
}
