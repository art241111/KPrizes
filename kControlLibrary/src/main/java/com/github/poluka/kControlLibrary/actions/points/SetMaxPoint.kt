package com.github.poluka.kControlLibrary.actions.points

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.actions.move.MoveToPoint
import com.github.poluka.kControlLibrary.dsl.Program
import com.github.poluka.kControlLibrary.enity.Axes
import com.github.poluka.kControlLibrary.enity.TypeOfMovement
import com.github.poluka.kControlLibrary.enity.position.Point

private const val SET_MAX_POINT = "POINTS;MAX;"

@ExecutedOnTheRobot
data class SetMaxPoint(
    private val point: Point,
) : Command {
    override fun run(): String {
        return SET_MAX_POINT + point.toString()
    }
}

fun Program.setMaxPoint(
    point: Point,
) = add(SetMaxPoint(point))
