package com.github.poluka.kControlLibrary.handlers.programStatusHandler

import com.github.poluka.kControlLibrary.sender.ProgramStatus
import com.github.poluka.kControlLibrary.sender.ProgramStatusEnum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProgramStatusHandler(
    private val statusUpdate: ProgramStatusUpdate,
    private val incomingText: StateFlow<String>
) {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job) // Add handlers to Reader
    private lateinit var statusJob: Job

//    val status =

    fun startHandleStatus() {
        statusJob = scope.launch {
            incomingText.collect { text ->
                if (text.contains("PROGRAM")) {
                    if (text.contains("SUCCESSFULLY")) {
                        statusUpdate.whenProgramStatusUpdate(
                            ProgramStatus(status = ProgramStatusEnum.READY_TO_SEND)
                        )
                    } else if (text.contains("ERROR")) {
                        statusUpdate.whenProgramStatusUpdate(
                            ProgramStatus(status = ProgramStatusEnum.ERROR)
                            // TODO: add error text
                        )
                    }
                }
            }
        }
    }

    fun stopHandleStatus() {
    }
}
