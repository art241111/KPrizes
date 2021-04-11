package com.github.poluka.kControlLibrary.actions.service.signal

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.dsl.Program

private const val command = "SERVICE;SIGNAL"

/**
 * This command sends a signal to the robot.
 * @param signal - the signal that is sent to the robot.
 *
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */
@ExecutedOnTheRobot
class Signal(private val signal: Int) : Command {
    override fun run(): String = "$command;$signal"
}

infix fun Program.signalOn(signal: Int) = add(Signal(signal))

infix fun Program.signalOff(signal: Int) = add(Signal(-signal))
