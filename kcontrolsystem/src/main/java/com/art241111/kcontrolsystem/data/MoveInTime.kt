package com.art241111.kcontrolsystem.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.abs

const val DELAY_SEND_SP = "DELAY_SEND"
const val LONG_MOVE_SP = "LONG_MOVE"
const val SHORT_MOVE_SP = "SHORT_MOVE"

class MoveInTime(
    /**
     * Задержка отправки сообщения
     */
    var delaySending: Long = 70L,
    var defaultButtonDistanceLong: Double = 10.0,
    var defaultButtonDistanceShort: Double = 1.0,
    private val move: (x: Double, y: Double, z: Double, o: Double, a: Double, t: Double) -> Unit
) {
    private var isMoving = false

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private var moveDistance = Array(6) { 0.0 }

    /**
     * Получаем доступ через координаты.
     * @param axes - координата, по которой нужно олучить значения.
     */
    operator fun get(axes: Int) =
        moveDistance[axes]

    /**
     * Изменяем значения через координаты.
     * @param axes - координата, по которой нужно изменить значения,
     * @param value - значение, которое нужно установить.
     */
    operator fun set(axes: Int, value: Double) {
        if (value == -0.0) moveDistance[axes] = 0.0
        else moveDistance[axes] = value

        if (isNull(moveDistance)) {
            isMoving = false
            stopMoving()
        } else {
            if (!isMoving) {
                isMoving = true
                startMoving()
            }
        }
    }

    private fun isNull(array: Array<Double>): Boolean {
        var result = true
        for (element in array) {
            if (abs(element) != 0.0) result = false
        }
        return result
    }

    /**
     * Старт перемещения по тем координатам, что заданы в массиви moveDistance
     */
    private lateinit var jobMoving: Job

    private fun startMoving() {
        if (!this::jobMoving.isInitialized || !jobMoving.isActive) {
            jobMoving = scope.launch {
                while (isMoving && this.isActive) {
                    if (isMoving) {
                        if (!isNull(moveDistance)) {
                            move(
                                moveDistance[0],
                                moveDistance[1],
                                moveDistance[2],
                                moveDistance[3],
                                moveDistance[4],
                                moveDistance[5]
                            )
                        } else {
                            isMoving = false
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
    private fun stopMoving() {
        isMoving = false
        moveDistance = Array(6) { 0.0 }

        if (this::jobMoving.isInitialized) jobMoving.cancel()
    }
}
