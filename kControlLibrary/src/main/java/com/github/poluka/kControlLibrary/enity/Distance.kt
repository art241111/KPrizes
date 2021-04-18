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
    override fun toString(): String {
        return "$x;$y;$z;$o;$a;$t;"
    }
}
