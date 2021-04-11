package com.art241111.kprizes.ui.timer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    fun start(time: Long) {
        if (isFirstStart) {
            isFirstStart = false
            isWork = true

            viewModelScope.launch(Dispatchers.IO) {
                while (_progress.value > 0) {
                    if (isWork) {
                        _progress.value = progress.value - 0.001
                        delay(time / 1000)
                    }
                }
            }
        }
    }

    fun stop() {
        isWork = false
        isFirstStart = true
    }

    fun resettingProgress() {
        _progress.value = 1.0
    }
}
