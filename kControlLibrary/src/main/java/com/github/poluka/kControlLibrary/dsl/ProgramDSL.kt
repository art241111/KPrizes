package com.github.poluka.kControlLibrary.dsl

import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot

/**
 * DSL метод, за счет которого можно создавать свои программы.
 * @param dslCommands - команды, которые нужно добавить в программу.
 */

fun kProgram(dslCommands: Program.() -> Unit) =
    Program(robot = "Kawasaki").apply(dslCommands).build()

/**
 * Класс, который позволяет добавлять команды в програму.
 * @author artem241120@gmail.com (Artem Gerasimov)
 */
@ExecutedOnTheRobot
class Program(val robot: String) {
    val commands: MutableList<Command> = mutableListOf()

    fun subProgram(subProgram: Program.() -> Unit) {
        val programBlockContainer = Program(robot).apply(subProgram)
        commands.addAll(programBlockContainer.commands)
    }

    fun add(command: Command) {
        commands.add(command)
    }

    fun add(program: Program) {
        commands.addAll(program.commands)
    }

    internal fun build(): Program = this
}
