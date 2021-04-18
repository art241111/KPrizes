package com.art241111.kprizes.ui.visionGame

import com.art241111.kprizes.ui.catHelper.OpenCatTextVM

/**
 * View model for updating phrases on the tint screen
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class CatTextVisionScreenVM : OpenCatTextVM() {
    override val possibleText: List<String>
        get() = listOf(
            "Перемещайти рукой, чтобы управлять роботом",
            "Для того, чтобы закрыть захват, сожмите руку в кулак",
        )
}
