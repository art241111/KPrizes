package com.github.poluka.kControlLibrary.enity

/**
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class Distance(
    val x: Double = 0.0,
    val y: Double = 0.0,
    val z: Double = 0.0,
    val o: Double = 0.0,
    val a: Double = 0.0,
    val t: Double = 0.0
) {
    /**
     * Получаем доступ через координаты.
     * @param axes - координата, по которой нужно олучить значения.
     */
    operator fun get(axes: Axes): Double {
        return when (axes) {
            Axes.X -> x
            Axes.Y -> y
            Axes.Z -> z
            Axes.O -> o
            Axes.A -> a
            Axes.T -> t
        }
    }

    operator fun get(axes: Int): Double {
        return when (axes) {
            0 -> x
            1 -> y
            2 -> z
            3 -> o
            4 -> a
            5 -> t
            else -> 0.0
        }
    }

    override fun toString(): String {
        return "$x;$y;$z;$o;$a;$t;"
    }

    operator fun times(other: Int): Distance {
        return Distance(
            x = x * other,
            y = y * other,
            z = z * other,
            o = o * other,
            a = a * other,
            t = t * other,
        )
    }

    operator fun times(other: Double): Distance {
        return Distance(
            x = x * other,
            y = y * other,
            z = z * other,
            o = o * other,
            a = a * other,
            t = t * other,
        )
    }
}
