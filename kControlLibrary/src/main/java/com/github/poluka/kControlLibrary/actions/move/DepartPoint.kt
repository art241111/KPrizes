package com.github.poluka.kControlLibrary.actions.move

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.dsl.Program
import com.github.poluka.kControlLibrary.enity.Axes
import com.github.poluka.kControlLibrary.enity.TypeOfMovement
import com.github.poluka.kControlLibrary.enity.position.Point

/**
 * This command allows you to move the robot at a distance from the point.
 * @param point - the point to move to.
 * @param typeOfMovement - type of he robot movement.
 * @param angle - the angle at which the gripper will turn.
 * @param dX - X-axis distance
 * @param dY - Y-axis distance
 * @param dZ - Z-axis distance
 * @param dO - O-axis distance
 * @param dA - A-axis distance
 * @param dT - T-axis distance
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
@ExecutedOnTheRobot
data class DepartPoint(
    private val point: Point,
    private val typeOfMovement: TypeOfMovement = TypeOfMovement.LMOVE,
    private val angle: Double = 0.0,
    private val dX: Double = 0.0,
    private val dY: Double = 0.0,
    private val dZ: Double = 0.0,
    private val dO: Double = 0.0,
    private val dA: Double = 0.0,
    private val dT: Double = 0.0
) : Command {
    override fun run(): String {
        val newPosition = Point(
            x = point[Axes.X] + dX,
            y = point[Axes.Y] + dY,
            z = point[Axes.Z] + dZ,
            o = point[Axes.O] + dO,
            a = point[Axes.A] + dA,
            t = point[Axes.T] + dT + angle
        )
        return MoveToPoint(newPosition, typeOfMovement).run()
    }
}

fun Program.departPoint(
    point: Point,
    typeOfMovement: TypeOfMovement = TypeOfMovement.LMOVE,
    dX: Double = 0.0,
    dY: Double = 0.0,
    dZ: Double = 0.0,
    dO: Double = 0.0,
    dA: Double = 0.0,
    dT: Double = 0.0
) = add(DepartPoint(point, typeOfMovement, dX, dY, dZ, dO, dA, dT))
