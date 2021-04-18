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
    private val _moveDistance: MutableStateFlow<Distance> = MutableStateFlow(Distance())
    val moveDistance: StateFlow<Distance> = _moveDistance
    var gripperState: Boolean = false
        private set

    private var isHandling = false
    suspend fun handlePosition() {
        isHandling = true

        incomingText.collect { text ->
            val newPosition = positionParsing(text)
            if (newPosition != null) {
                oldPosition = newPosition
                gripperState = text.substringAfterLast(";").trim() == "1"
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
     * @return new position - when position change,
     * @return null - if old position.
     */
    private fun positionParsing(position: String): Point? {
        val newPosition = Point().positionArrayFromString(";$position")

        return if (newPosition != oldPosition) {
            if (!oldPosition.isNull()) {
                _moveDistance.value = newPosition - oldPosition
            }

            Log.d("SERVER_VISION", "${_moveDistance.value}")
            newPosition
        } else {
            null
        }
    }
}
