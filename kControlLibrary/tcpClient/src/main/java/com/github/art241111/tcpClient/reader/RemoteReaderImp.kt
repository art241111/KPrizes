package com.github.art241111.tcpClient.reader

import java.net.Socket

/**
 * Interface that should implement the class that
 * will connect to read input data from the server.
 * @author Artem Gerasimov.
 */
interface RemoteReaderImp {
    fun destroyReader()
    fun createReader(socket: Socket)
}
