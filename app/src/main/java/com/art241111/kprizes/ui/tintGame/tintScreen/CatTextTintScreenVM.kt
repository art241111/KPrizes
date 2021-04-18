package com.art241111.kprizes.ui.tintGame.tintScreen

import com.art241111.kprizes.ui.catHelper.OpenCatTextVM

/**
 * View model for updating phrases on the tint screen
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class CatTextTintScreenVM : OpenCatTextVM() {
    override val possibleText: List<String>
        get() = listOf(
            "Наклоняйте планшет, чтобы управлять роботом",
        )
}
