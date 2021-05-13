package com.art241111.kprizes.repository

import android.util.Log
import com.github.poluka.kControlLibrary.enity.Distance
import com.github.poluka.kControlLibrary.enity.position.Point
import com.github.poluka.kControlLibrary.enity.position.positionArrayFromString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

/**
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class MovePositionHandler(private val incomingText: SharedFlow<String>) {
    /**
     * State the position that you want to monitor
     * to obtain information about the position.
     */
    private var oldPosition: Point = Point()
    private val _moveDistance: MutableStateFlow<Point> = MutableStateFlow(Point())
    val moveDistance: StateFlow<Point> = _moveDistance

    private val _gripperState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val gripperState: StateFlow<Boolean> = _gripperState


    var scale: Double = 1.0

    private var isHandling = false
    suspend fun handlePosition() {
        isHandling = true

        incomingText.collect { text ->
            val newPosition = positionParsing(text, scale)
            if (newPosition != null) {
                oldPosition = newPosition
                _gripperState.value = text.substringAfterLast(";").trim() == "1"

            }
            if (!isHandling) return@collect
        }
    }

    fun stopHandlePosition() {
        isHandling = false
    }

    /**
     * Parse input data.
     * @param position - data to parse,
     * @param scale - коэфициент, на который изменяется входная дистанция
     * @return new position - when position change,
     * @return null - if old position.
     */
    private fun positionParsing(position: String, scale: Double = 1.0): Point? {
        val newPosition = Point().positionArrayFromString(";$position")

        _moveDistance.value = newPosition

        return if (newPosition != oldPosition) {
//            if (!oldPosition.isNull()) {
////                _moveDistance.value = (newPositxion - oldPosition)
//
////                _moveDistance.value = newPosition
//            }

            newPosition
        } else {
            null
        }
    }
}
