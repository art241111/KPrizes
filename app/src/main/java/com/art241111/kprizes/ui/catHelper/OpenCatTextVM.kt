package com.art241111.kprizes.ui.catHelper

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel

/**
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

abstract class OpenCatTextVM : ViewModel() {
    abstract val possibleText: List<String>

    private val catTextVM = CatTextVM()

    /**
     * The state that changes when the text changes.
     * Required for animating text changes in the assistant cat.
     */
    val isUpdate: State<Boolean> = catTextVM.isUpdate

    /**
     * The text that the assistant cat is currently saying.
     */
    val text: State<String> = catTextVM.text

    /**
     * Starting the text update for the assistant cat.
     */
    private var isRun = false
    fun startUpdate() {
        if (!isRun) {
            isRun = true

            catTextVM.startUpdate(
                listOf(
                    "Привет!",
                    "Давай сыграем в игру!",
                    "Вытяни игрушку с помощью промышленного робота "
                )
            )
        }
    }

    /**
     * Stopping text updates.
     */
    fun stopUpdate() {
        isRun = false
        catTextVM.stopUpdate()
    }
}
