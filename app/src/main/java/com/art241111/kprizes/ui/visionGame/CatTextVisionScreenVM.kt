package com.art241111.kprizes.ui.visionGame

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.art241111.kprizes.ui.catHelper.CatTextVM

/**
 * View model for updating phrases on the tint screen
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class CatTextVisionScreenVM : ViewModel() {
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
                    "Перемещайти рукой, чтобы управлять роботом",
                    "Для того, чтобы закрыть захват, сожмите руку в кулак",
                    // "Вытяни игрушку с помощью промышленного робота "
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
