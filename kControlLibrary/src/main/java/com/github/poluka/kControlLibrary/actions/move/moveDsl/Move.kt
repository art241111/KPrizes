package com.github.poluka.kControlLibrary.actions.move.moveDsl

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.move.MoveByAxes
import com.github.poluka.kControlLibrary.actions.move.MoveOnDistance
import com.github.poluka.kControlLibrary.actions.move.MoveToPoint
import com.github.poluka.kControlLibrary.dsl.Program
import com.github.poluka.kControlLibrary.enity.Axes
import com.github.poluka.kControlLibrary.enity.Distance
import com.github.poluka.kControlLibrary.enity.position.Point

/**
 * Extension class for creating infix commands.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class Move {
    companion object {
        infix fun to(point: Point): Command {
            return MoveToPoint(point)
        }

        infix fun Program.to(point: Point) {
            add(MoveToPoint(point))
        }

        infix fun by(distance: Distance): Command {
            return MoveOnDistance(distance)
        }

        infix fun Program.by(distance: Distance) {
            add(MoveOnDistance(distance))

            // for(i in 1 until 10 step 2) { // Same as - for(i in 1.until(10).step(2))
            //     print("$i ")
            // }
        }

        infix fun by(axes: Axes): MoveByAxes =
            MoveByAxes(axes, 0.0)
    }
}
