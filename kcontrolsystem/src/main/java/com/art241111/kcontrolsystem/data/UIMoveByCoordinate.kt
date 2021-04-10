package com.art241111.kcontrolsystem.data

import androidx.compose.runtime.State
import com.github.poluka.kControlLibrary.enity.position.Point

interface UIMoveByCoordinate {
    fun moveByX(point: Double)
    fun moveByY(point: Double)
    fun moveByZ(point: Double)
    fun moveByO(point: Double)
    fun moveByA(point: Double)
    fun moveByT(point: Double)
}

class UIMoveByCoordinateKRobot(
    private val positionState: State<Point>,
    private val move: (point: Point) -> Unit
) : UIMoveByCoordinate {
    override fun moveByX(point: Double) {
        moveByCoordinate(0, point)
    }

    override fun moveByY(point: Double) {
        moveByCoordinate(1, point)
    }

    override fun moveByZ(point: Double) {
        moveByCoordinate(2, point)
    }

    override fun moveByO(point: Double) {
        moveByCoordinate(3, point)
    }

    override fun moveByA(point: Double) {
        moveByCoordinate(4, point)
    }

    override fun moveByT(point: Double) {
        moveByCoordinate(5, point)
    }

    private fun moveByCoordinate(axes: Int, point: Double) {
        val position = positionState.value
        position[axes] = point

        move(position)
    }
}
