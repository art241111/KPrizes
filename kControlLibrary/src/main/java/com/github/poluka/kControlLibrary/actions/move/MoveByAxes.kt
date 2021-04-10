package com.github.poluka.kControlLibrary.actions.move

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.dsl.Program
import com.github.poluka.kControlLibrary.enity.Axes

private const val MOVE_BY_COORDINATE = "MOVE;BASE"

/**
 * Moving a certain distance on one axis.
 * @param axes - coordinates that are used for moving,
 * @param distance - the distance to which the movement takes place.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
@ExecutedOnTheRobot
data class MoveByAxes(
    private val axes: Axes,
    private var distance: Double
) : Command {

    /**
     * Start program.
     */
    override fun run(): String {
        val arrayMoving = Array(6) { 0.0 }
        arrayMoving[axes.ordinal] = distance
        return "$MOVE_BY_COORDINATE;${arrayMoving.joinToString(separator = ";")}"
    }
//    "$MOVE_BY_COORDINATE;${(coordinate.ordinal + 1)};$distance"

    internal infix fun on(_distance: Double) {
        distance = _distance
    }
}

fun Program.moveByAxes(axes: Axes, distance: Double) = add(MoveByAxes(axes, distance))
