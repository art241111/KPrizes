package com.github.art241111.tcpClient.connection

import kotlinx.coroutines.flow.MutableStateFlow
import java.net.InetSocketAddress
import java.net.Socket

/**
 * Create connection class.
 * @author Artem Gerasimov.
 */
class Connection {
    var socket = Socket()

    val statusState: MutableStateFlow<Status?> = MutableStateFlow(Status.DISCONNECTED)

    /**
     * Disconnect from server.
     */
    fun disconnect() {
        if (statusState.value == Status.COMPLETED) {
            socket.close()
            statusState.value = Status.DISCONNECTED

            socket = Socket()
        }
    }

    /**
     * Connect to server by TCP/IP.
     * If the connection did not occur within 2 seconds,
     * the error status is displayed.
     */
    fun connect(address: String, port: Int) = connectToTheServer(address, port)

    private fun connectToTheServer(address: String, port: Int) {
        if (statusState.value != Status.CONNECTING && statusState.value != Status.COMPLETED) {
            try {
                // Set connecting status
                statusState.value = Status.CONNECTING

                // Try to connect
                socket.connect(InetSocketAddress(address, port), 2000)

                // If the connection is successful, we notify you about it
                statusState.value = Status.COMPLETED
            } catch (e: Exception) {
                statusState.value = Status.ERROR
                socket = Socket()
            }
        }
    }
}
