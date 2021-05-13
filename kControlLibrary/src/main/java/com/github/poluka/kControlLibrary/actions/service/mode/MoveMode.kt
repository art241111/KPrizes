package com.github.poluka.kControlLibrary.actions.service.mode

import com.github.poluka.kControlLibrary.actions.Command

/**
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

class MoveMode(private val isModeOn: Boolean) : Command {
    override fun run(): String {
        return if (isModeOn)
            "MOVE_MODE;TRUE;"
        else
            "MOVE_MODE;FALSE;"
    }
}
