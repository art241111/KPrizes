package com.github.poluka.kControlLibrary.actions.move

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.dsl.Program
import com.github.poluka.kControlLibrary.enity.position.Point

private const val MOVE_BY_C = "CMOVE"

@ExecutedOnTheRobot
class MoveC(
    private val pointOnArc: Point,
    private val endPoint: Point
) : Command {

    override fun run(): String = "$MOVE_BY_C;$pointOnArc;$endPoint"
}

fun Program.moveByArc(
    pointOnArc: Point,
    endPoint: Point
) = add(MoveC(pointOnArc, endPoint))
