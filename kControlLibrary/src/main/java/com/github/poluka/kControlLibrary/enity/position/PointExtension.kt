package com.github.poluka.kControlLibrary.enity.position

/**
 * An extension for the [Point] class that allows you to get a value from a string.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

fun String.toPoint(): Point {
    val points = this.split(";")
    return Point(
        x = points[0].toDouble(),
        y = points[1].toDouble(),
        z = points[2].toDouble(),
        o = points[3].toDouble(),
        a = points[4].toDouble(),
        t = points[5].toDouble(),
    )
}
