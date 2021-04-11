package com.github.poluka.kControlLibrary.actions.delay

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.dsl.Program

private const val command = "SERVICE;WSIGNAL"

/**
 * This command wait, when the robot get this signal.
 * @param signal - the signal that the robot is waiting for.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
@ExecutedOnTheRobot
class WaitingSignal(private val signal: Int) : Command {
    override fun run(): String = "$command;$signal"
}

infix fun Program.waitSignal(signal: Int) = add(WaitingSignal(signal))
