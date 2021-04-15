package com.github.poluka.kControlLibrary.enity.position

import com.github.poluka.kControlLibrary.enity.Axes
import com.github.poluka.kControlLibrary.enity.Distance

/**
 * Массив, который хранит позицию робота.
 * @author artem241120@gmail.com
 */
class Point(
    private val x: Double = 0.0,
    private val y: Double = 0.0,
    private val z: Double = 0.0,
    private val o: Double = 0.0,
    private val a: Double = 0.0,
    private val t: Double = 0.0
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
            x = x - point.x,
            y = y - point.y,
            z = z - point.z,
            o = o - point.o,
            a = a - point.a,
            t = t - point.t,
        )
    }
}
