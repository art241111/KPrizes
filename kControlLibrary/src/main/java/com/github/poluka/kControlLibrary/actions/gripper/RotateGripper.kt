package com.github.poluka.kControlLibrary.actions.gripper

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.move.MoveByAxes
import com.github.poluka.kControlLibrary.dsl.Program
import com.github.poluka.kControlLibrary.enity.Axes

data class RotateGripper(val angleOfRotation: Double) : Command {
    override fun run(): String =
        MoveByAxes(Axes.T, angleOfRotation).run()
}

fun Program.rotateGripper(angleOfRotation: Double) = add(RotateGripper(angleOfRotation))
