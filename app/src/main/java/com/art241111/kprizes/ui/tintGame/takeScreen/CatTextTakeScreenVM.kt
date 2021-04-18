package com.art241111.kprizes.ui.tintGame.takeScreen

import com.art241111.kprizes.ui.catHelper.OpenCatTextVM

/**
 * View model for updating phrases on the take screen
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class CatTextTakeScreenVM : OpenCatTextVM() {
    override val possibleText: List<String>
        get() = listOf(
            "Нажмите на кнопку, когда будете уверены, что робот возьмет игрушку",
        )
}
