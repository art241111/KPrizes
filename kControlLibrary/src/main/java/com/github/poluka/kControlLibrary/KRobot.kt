package com.github.poluka.kControlLibrary

import com.github.art241111.tcpClient.Client
import com.github.art241111.tcpClient.connection.Status
import com.github.poluka.kControlLibrary.actions.Command
import com.github.poluka.kControlLibrary.actions.annotation.ExecutedOnTheRobot
import com.github.poluka.kControlLibrary.dsl.Program
import com.github.poluka.kControlLibrary.enity.position.Point
import com.github.poluka.kControlLibrary.handlers.PositionHandler
import com.github.poluka.kControlLibrary.sender.SenderForRobot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class KRobot {
    private val client = Client()
    private val sender = SenderForRobot(client.getSender(), client.incomingText)
    private val positionHandler = PositionHandler(client.incomingText)

    val programState = sender.programStatus
    val positionState = positionHandler.positionState
    val statusState: MutableStateFlow<Status?> = client.statusState

    private var isConnect = false

    var homePosition = Point(0.0, 515.0, 242.0, 90.0, 180.0, 0.0)

    infix fun run(@ExecutedOnTheRobot command: Command) {
        if (isConnect) {
            sender.safeSend(command.run())
        }
    }

    /**
     * Данный метод нужно использовать только тогда, когда не важно выполниться ли команда
     */
    infix fun dangerousRun(@ExecutedOnTheRobot command: Command) {
        if (isConnect) {
            sender.send(command.run())
        }
    }

    infix fun run(@ExecutedOnTheRobot program: Program) {
        if (isConnect) {
            program.commands.forEach { command ->
                sender.safeSend(command.run())
            }
        }
    }

    /**
     * Launch the program after connecting to the robot.
     * Made for security, so that before connecting to the robot,
     * there are no commands other than those that are specifically set.
     */
    private lateinit var programWhenConnect: Command
    infix fun runWhenConnect(@ExecutedOnTheRobot program: Command) {
        programWhenConnect = program
    }

    fun connect(address: String, port: Int) {
        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.IO + job) // Add handlers to Reader

        scope.launch {
            client.connect(address, port)

            if (client.status == Status.COMPLETED) {
//                sender.programStatusHandler.startHandleStatus()
                positionHandler.handlePosition()

                sender.startSending()

                if (this@KRobot::programWhenConnect.isInitialized) {
                    run(programWhenConnect)
                }

                isConnect = true
            }
        }
    }

    fun disconnect() {
        isConnect = false

        client.disconnect()
        positionHandler.stopHandlePosition()
//        sender.programStatusHandler.stopHandleStatus()

        sender.stopSending()
    }
}
