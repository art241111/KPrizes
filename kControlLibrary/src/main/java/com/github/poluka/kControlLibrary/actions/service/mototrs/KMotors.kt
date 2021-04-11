package com.github.poluka.kControlLibrary.actions.service.mototrs

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.dsl.Program

private const val command = "MOTOR"

@ExecutedOnTheRobot
class KMotors(private val kMotorStatus: KMotorStatus) : Command {
    override fun run(): String {
        return "$command;${kMotorStatus.name}"
    }
}

fun Program.motorOn() = add(KMotors(KMotorStatus.ON))

// fun Program.motorOn() = signalOn(2130)// doWithCommand(Signal(2130))
fun Program.motorOff() = add(KMotors(KMotorStatus.OFF))
