package com.github.poluka.kControlLibrary.enity.position

import com.github.poluka.kControlLibrary.enity.Axes
import com.github.poluka.kControlLibrary.enity.Distance
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Массив, который хранит позицию робота.
 * @author artem241120@gmail.com
 */
class Point(
    x: Double = 0.0,
    y: Double = 0.0,
    z: Double = 0.0,
    o: Double = 0.0,
    a: Double = 0.0,
    t: Double = 0.0
) {
    private val position = doubleArrayOf(x, y, z, o, a, t)

    constructor(x: Int, y: Int = 0, z: Int = 0, o: Int = 0, a: Int = 0, t: Int = 0) : this(
        x.toDouble(),
        y.toDouble(),
        z.toDouble(),
        o.toDouble(),
        a.toDouble(),
        t.toDouble()
    )

    /**
     *
     */
    override fun toString(): String {
        return position.joinToString(separator = ";")
    }

    /**
     * Получаем доступ через координаты.
     * @param axes - координата, по которой нужно олучить значения.
     */
    operator fun get(axes: Axes) = position[axes.ordinal]
    operator fun get(axes: Int): Double = position[axes]

    /**
     * Изменяем значения через координаты.
     * @param axes - координата, по которой нужно изменить значения,
     * @param value - значение, которое нужно установить.
     */
    operator fun set(axes: Axes, value: Double) {
        position[axes.ordinal] = value
    }

    operator fun set(axes: Int, value: Double) {
        position[axes] = value
    }

    operator fun minus(point: Point): Distance {
        return Distance(
            x = sqrt((position[0] - point.position[0]).pow(2)),
            y = sqrt((position[1] - point.position[1]).pow(2)),
            z = sqrt((position[2] - point.position[2]).pow(2)),
            o = sqrt((position[3] - point.position[3]).pow(2)),
            a = sqrt((position[4] - point.position[4]).pow(2)),
            t = sqrt((position[5] - point.position[5]).pow(2)),
        )
    }

    operator fun plus(point: Point): Distance {
        return Distance(
            x = position[0] + point.position[0],
            y = position[1] + point.position[1],
            z = position[2] + point.position[2],
            o = position[3] + point.position[3],
            a = position[4] + point.position[4],
            t = position[5] + point.position[5],
        )
    }

    fun isNull(): Boolean {
        var isEm = true

        for (pos in position) {
            if (pos != 0.0) {
                isEm = false
                break
            }
        }

        return isEm
    }
}
