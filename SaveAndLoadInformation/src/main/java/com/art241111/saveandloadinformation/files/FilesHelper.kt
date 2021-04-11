package com.art241111.saveandloadinformation.files

import android.content.Context
import com.art241111.saveandloadinformation.files.protocols.ReadFromFile
import com.art241111.saveandloadinformation.files.protocols.WriteToFile
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class FilesHelper : WriteToFile, ReadFromFile {
    override fun writeToFile(
        fileName: String,
        fileText: String,
        context: Context
    ): Boolean {
        try {
            // отрываем поток для записи
            val bw = BufferedWriter(
                OutputStreamWriter(
                    context.openFileOutput(fileName, Context.MODE_PRIVATE)
                )
            )
            // пишем данные
            bw.write(fileText)
            // закрываем поток
            bw.close()
            return true
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return false
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
    }

    override fun readFromFile(
        fileName: String,
        context: Context,
        handler: (String) -> Unit
    ) {
        var str: String?

        try {
            // открываем поток для чтения
            val br = BufferedReader(
                InputStreamReader(
                    context.openFileInput(fileName)
                )
            )

            // читаем содержимое
            while (br.readLine().also { str = it } != null) {
                if (str != null) {
                    handler(str!!)
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
