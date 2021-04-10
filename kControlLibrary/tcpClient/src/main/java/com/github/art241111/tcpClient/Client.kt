package com.github.art241111.tcpClient

import com.github.art241111.tcpClient.connection.Connection
import com.github.art241111.tcpClient.connection.Status
import com.github.art241111.tcpClient.reader.RemoteReader
import com.github.art241111.tcpClient.writer.RemoteWriter
import com.github.art241111.tcpClient.writer.SafeSender
import com.github.art241111.tcpClient.writer.Sender
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.Socket

/**
 * TCP client.
 * @author Artem Gerasimov.
 */
class Client() {
    private val connection = Connection()
    private val remoteReader = RemoteReader()
    private val remoteWriter = RemoteWriter()
    fun getSender(): Sender = remoteWriter
    fun getSafeSender(): SafeSender = remoteWriter

    val incomingText = remoteReader.incomingText

    /**
     * @return connect status
     */
    val statusState: MutableStateFlow<Status?> = connection.statusState

    var status = Status.DISCONNECTED

    /**
     * Connect to TCP sever.
     * @param address - server ip port,
     * @param port - server port.
     *
     * TODO: Пересмотреть if и status
     */
    suspend fun connect(
        address: String,
        port: Int,
        senderDelay: Long = 0L
    ) {
        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.IO + job) // Add handlers to Reader

        // When the device connects to the server, it creates Reader and Writer
        withContext(scope.coroutineContext) {
            connection.connect(address, port)
            status = Status.CONNECTING

            while (connection.statusState.value != Status.ERROR) {
                // When the device connects to the server, it creates Reader and Writer
                if (connection.socket.isConnected) {
                    startReadingAndWriting(socket = connection.socket, senderDelay)
                    status = Status.COMPLETED
                    break
                } else {
                    status = Status.ERROR
                    this.cancel()
                }
            }
        }
    }

    /**
     * Disconnect from TCP sever.
     */
    fun disconnect() {
        GlobalScope.launch {
            remoteReader.destroyReader()
            remoteWriter.destroyWriter()

            delay(50L)
            connection.disconnect()
            status = Status.DISCONNECTED
        }
    }

    /**
     * Send text to the server.
     */
    fun send(text: String) {
        remoteWriter.send(text)
    }

    private fun startReadingAndWriting(socket: Socket, delay: Long) {
        remoteReader.createReader(socket)
        remoteWriter.createWriter(socket, delay)
    }
}
