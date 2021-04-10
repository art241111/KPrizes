package com.art241111.kprizes.ui.timer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    init {
        start(60000L)
    }

    fun start(time: Long) {
        if (isFirstStart) {
            isFirstStart = false
            _progress.value = 1.0

            viewModelScope.launch {
                while (_progress.value > 0) {
                    _progress.value = progress.value - 0.001
                    delay(time / 1000)
                }
            }
        }
    }

    fun stop() {
        isFirstStart = true
    }
}
