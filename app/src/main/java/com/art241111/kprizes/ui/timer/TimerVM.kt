package com.art241111.kprizes.ui.timer

import android.os.CountDownTimer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

/**
 * A timer that changes progress while running.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class TimerVM : ViewModel() {
    private val _progress = mutableStateOf(1.0)
    val progress: State<Double> = _progress

    private var isFirstStart = true
    private var isWork = true

    private lateinit var timer: CountDownTimer

    fun start(time: Long) {

        if (isFirstStart) {
            isFirstStart = false

            timer = object : CountDownTimer(time, 100) {
                override fun onTick(millisUntilFinished: Long) {
                    _progress.value = millisUntilFinished / time.toDouble()
                }

                override fun onFinish() {
                    _progress.value = 0.0
                }
            }

            timer.start()
        }
    }

    fun stop() {
        if (::timer.isInitialized) {
            timer.cancel()
        }

        isFirstStart = true
    }

    fun resettingProgress() {
        _progress.value = 1.0
    }
}
