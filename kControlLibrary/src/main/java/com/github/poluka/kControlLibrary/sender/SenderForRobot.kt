package com.github.poluka.kControlLibrary.sender

import com.github.art241111.tcpClient.writer.SafeSender
import com.github.art241111.tcpClient.writer.Sender
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.LinkedList
import java.util.Queue

internal class SenderForRobot(
    private val sender: Sender,
    private val incomingText: SharedFlow<String>
) : Sender, SafeSender {
    var programStatus: StateFlow<ProgramStatus> = MutableStateFlow(ProgramStatus(ProgramStatusEnum.READY_TO_SEND))
    private val sendQueue: Queue<String> = LinkedList()
    private var isWriting = false

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    /**
     * Add message to queue.
     * @param text - the text that will be sent to the server.
     */
    override fun send(text: String) {
        sender.send(text)
    }

    override fun safeSend(text: String) {
        sendQueue.add(text)
    }

    /**
     * Handle queue.
     */
    private fun startSend() {
        scope.launch {
            while (isWriting) {
                if (sendQueue.isNotEmpty() && programStatus.value.status == ProgramStatusEnum.READY_TO_SEND) {
                    programStatus.value.status = ProgramStatusEnum.PROGRAM_IS_RUNNING
                    val text = sendQueue.poll()
                    if (!text.isNullOrEmpty()) {
                        if (text.contains("Delay")) {
                            delay(text.substringAfter(":").toLong())
                        } else {
                            send(text)
                        }
                    }
                    delay(5L)
                } else if (sendQueue.isEmpty()) {
                    programStatus.value.status = ProgramStatusEnum.READY_TO_SEND
                }
            }
        }
    }

    /**
     * Sending the final command and closing Writer.
     */
    override fun stopSending() {
        isWriting = false
        sendQueue.clear()

        if (this::statusJob.isInitialized) {
            statusJob.cancel()
        }
    }

    override fun startSending() {
        programStatus.value.status = ProgramStatusEnum.READY_TO_SEND
        isWriting = true
        updateState()
        sendQueue.clear()
        startSend()
    }

    private val job2 = SupervisorJob()
    private val scope2 = CoroutineScope(Dispatchers.IO + job2) // Add handlers to Reader
    private lateinit var statusJob: Job

    private fun updateState() {
        statusJob = scope2.launch {
            if (this.isActive) {
                incomingText.collect { text ->

                    if (text.contains("PROGRAM")) {
                        if (text.contains("SUCCESSFULLY")) {
                            programStatus.value.status = ProgramStatusEnum.READY_TO_SEND
                        } else if (text.contains("ERROR")) {
                            programStatus.value.status = ProgramStatusEnum.ERROR
                            // TODO: add error text
                        }
                    }
                }
            }
        }
    }
}
