package com.art241111.kprizes.ui.catHelper

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * This class allows you to update the text that the cat assistant says.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class CatTextVM : ViewModel() {
    private val _isUpdate = mutableStateOf(false)

    /**
     * The state that changes when the text changes.
     * Required for animating text changes in the assistant cat.
     */
    val isUpdate: State<Boolean> = _isUpdate

    private val _text = mutableStateOf("")

    /**
     * The text that the assistant cat is currently saying.
     */
    val text: State<String> = _text

    private var state = -1
    private var update = true
    private var isFirstUpdate = true

    /**
     * Starting the text update for the assistant cat.
     * @param phrasesList - list of phrases that the cat assistant says.
     */
    fun startUpdate(phrasesList: List<String>) {
        if (phrasesList.size == 1) {
            _text.value = phrasesList[0]
        } else {
            if (isFirstUpdate) {
                isFirstUpdate = true

                viewModelScope.launch {
                    while (update) {
                        _isUpdate.value = true
                        delay(50L)

                        state = ++state % phrasesList.size
                        _text.value = phrasesList[state]

                        _isUpdate.value = false

                        delay(2500L)
                    }
                }
            }
        }
    }

    /**
     * Stopping text updates.
     */
    fun stopUpdate() {
        update = false
        isFirstUpdate = false
    }
}
