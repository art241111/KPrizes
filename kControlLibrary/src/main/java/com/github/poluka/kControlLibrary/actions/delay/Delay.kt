package com.github.poluka.kControlLibrary.actions.delay

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.dsl.Program

/**
 * Command to create a delay between commands.
 * @param delayTime - delay time.
 */
data class Delay(val delayTime: Long) : Command {
    override fun run(): String = "Delay:$delayTime"
}

fun Program.delay(delayTime: Long) = add(Delay(delayTime))
