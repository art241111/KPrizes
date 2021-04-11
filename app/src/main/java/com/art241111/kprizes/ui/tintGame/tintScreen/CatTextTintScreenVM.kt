package com.art241111.kprizes.ui.tintGame.tintScreen

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.art241111.kprizes.ui.catHelper.CatTextVM

/**
 * View model for updating phrases on the tint screen
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class CatTextTintScreenVM : ViewModel() {
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

    init {
        startUpdate()
    }

    /**
     * Starting the text update for the assistant cat.
     */
    fun startUpdate() {
        catTextVM.startUpdate(
            listOf(
                "Наклоняйте планшет, чтобы управлять роботом",
                // "Давай сыграем в игру!",
                // "Вытяни игрушку с помощью промышленного робота "
            )
        )
    }

    /**
     * Stopping text updates.
     */
    fun stopUpdate() {
        catTextVM.stopUpdate()
    }
}
