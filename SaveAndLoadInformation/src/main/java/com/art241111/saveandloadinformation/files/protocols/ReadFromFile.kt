package com.art241111.saveandloadinformation.files.protocols

import android.content.Context

interface ReadFromFile {
    fun readFromFile(fileName: String, context: Context, handler: (String) -> Unit)
}
