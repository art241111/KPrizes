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
    private val _commands: MutableList<Command> = mutableListOf()
    val commands: List<Command> = _commands

    fun subProgram(subProgram: Program.() -> Unit) {
        val programBlockContainer = Program(robot).apply(subProgram)
        _commands.addAll(programBlockContainer._commands)
    }

    infix fun add(command: Command) {
        _commands.add(command)
    }

    infix fun add(program: Program) {
        _commands.addAll(program._commands)
    }

    internal fun build(): Program = this
}
