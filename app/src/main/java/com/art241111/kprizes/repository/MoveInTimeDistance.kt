package com.art241111.kprizes.repository

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.github.poluka.kControlLibrary.enity.position.Point
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class MoveInTimeDistance(
    var delaySending: Long = 70L,
    private val move: (point: Point) -> Unit,
) {
    var newPosition = Point()

    private var isMoving = mutableStateOf(false)

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    /**
     * Старт перемещения по тем координатам, что заданы в массиви moveDistance
     */
    private lateinit var jobMoving: Job

    private fun isSmallMoving(point: Point): Boolean {
        var result = 0

        for (i in 0..2) {
            if (abs(point[i]) < 1) result++
        }

        return result < 3
    }

    fun startMoving() {
        isMoving.value = true

        if (!this::jobMoving.isInitialized || !jobMoving.isActive) {
            jobMoving = scope.launch {
                while (isMoving.value && this.isActive) {
                    if (isMoving.value) {
                        Log.d("new_point", newPosition.toString())
                        if (!isSmallMoving(newPosition)) {
                            move(newPosition)
                        } else {
                            isMoving.value = false
                            scope.cancel()
                            jobMoving.cancel()
                            this.cancel()
                        }
                        delay(delaySending)
                    }
                }
            }
        }
    }

    /**
     * Остановка цикла отправки и обнуление массива перемещений
     */
    fun stopMoving() {
        isMoving.value = false
        newPosition = Point()

        if (this::jobMoving.isInitialized) jobMoving.cancel()
    }
}
