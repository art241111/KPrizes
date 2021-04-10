package com.art241111.saveandloadinformation.files.protocols

import android.content.Context

interface WriteToFile {
    fun writeToFile(fileName: String, fileText: String, context: Context): Boolean
}
