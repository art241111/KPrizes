package com.art241111.kprizes.ui.startScreen

import com.art241111.kprizes.ui.catHelper.OpenCatTextVM

/**
 * View model for updating phrases on the first screen
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class CatTextMainScreenVM : OpenCatTextVM() {
    override val possibleText: List<String>
        get() = listOf(
            "Привет!",
            "Давай сыграем в игру!",
            "Вытяни игрушку с помощью промышленного робота "
        )
}
