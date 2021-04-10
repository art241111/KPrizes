package com.github.art241111.tcpClient.reader

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.Socket
import java.util.Scanner

/**
 * This class creates a Reader that will listen to the
 * incoming stream and process the data that the server sends.
 * @author Artem Gerasimov.
 */
class RemoteReader : RemoteReaderImp {
    private val _incomingText: MutableSharedFlow<String> = MutableSharedFlow()
    val incomingText: SharedFlow<String> = _incomingText

    // A variable that displays whether our reader is working.
    private var isReading = false

    // Reader for receiving and reading incoming data from the server
    private lateinit var reader: Scanner

    /**
     * Stop reading track.
     */
    override fun destroyReader() {
        isReading = false

        if (::reader.isInitialized && isReading) {
            GlobalScope.launch(Dispatchers.IO) {
                reader.close()
            }
        }
    }

    /**
     * Start reading from InputStream
     * @param socket - the connection that you want to listen to.
     */
    override fun createReader(socket: Socket) {
        reader = Scanner(socket.getInputStream())
        isReading = true

        GlobalScope.launch(Dispatchers.IO) {
            startTrackingInputString(reader)
        }
    }

    private suspend fun startTrackingInputString(reader: Scanner) {
        while (isReading) {
            try {
                val line = reader.nextLine()
                _incomingText.emit(line)
            } catch (e: NullPointerException) {
//                     Log.e("reader", "No elements come", e)
            } catch (e: Exception) {
//                    Log.e("reader", "Unknown error", e)
            }
        }
    }
}
