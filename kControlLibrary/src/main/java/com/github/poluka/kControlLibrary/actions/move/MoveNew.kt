package com.github.poluka.kControlLibrary.actions.move

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.dsl.Program
import com.github.poluka.kControlLibrary.enity.Distance

private const val MOVE_BY_COORDINATE = "MOVE_NEW"

/**
 * Moving a certain distance on one axis.
 * @param x - x-axis distance
 * @param y - y-axis distance
 * @param z - z-axis distance
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
@ExecutedOnTheRobot
data class MoveNew(
    private val x: Double = 0.0,
    private val y: Double = 0.0,
    private val z: Double = 0.0,
) : Command {

    constructor (distance: Distance) :
        this(
            distance.x, distance.y, distance.z,
        )

    /**
     * Start program.
     */
    override fun run(): String {
        return "$MOVE_BY_COORDINATE;$x;$y;$z;\n"
    }
}

fun Program.moveNew(
    x: Double = 0.0,
    y: Double = 0.0,
    z: Double = 0.0,
    o: Double = 0.0,
    a: Double = 0.0,
    t: Double = 0.0
) = add(MoveOnDistance(x, y, z, o, a, t))
