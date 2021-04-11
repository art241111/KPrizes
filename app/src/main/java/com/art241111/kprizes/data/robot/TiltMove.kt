package com.art241111.kprizes.data.robot

import com.art241111.kcontrolsystem.ui.utils.TiltMove
import com.github.poluka.kControlLibrary.actions.move.MoveOnDistance

/**
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class TiltMoveImp(private val robot: RobotVM) : TiltMove {
    override fun move(x: Double, y: Double) {
        robot dangerousRun MoveOnDistance(y, x)
    }
}
