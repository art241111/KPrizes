package com.github.poluka.kControlLibrary.actions.points

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.dsl.Program
import com.github.poluka.kControlLibrary.enity.position.Point

/**
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
private const val SET_MAX_POINT = "POINTS;MIDDLE;"

@ExecutedOnTheRobot
data class SetMiddlePoint(
    private val point: Point,
) : Command {
    override fun run(): String {
        return SET_MAX_POINT + point.toString()
    }
}

fun Program.setMiddlePoint(
    point: Point,
) = add(SetMaxPoint(point))
