package com.github.poluka.kControlLibrary.actions.points

import com.github.poluka.kControlLibrary.actions.Command

class SetIsInArea(private val isInArea: Boolean): Command {
    override fun run(): String {
        return if (isInArea)
            "IS_AREA_MODE;TRUE;"
        else
            "IS_AREA_MODE;FALSE;"
    }
}