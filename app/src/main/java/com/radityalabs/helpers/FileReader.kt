package com.radityalabs.helpers

import android.content.Context
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

interface FileReader {
    fun text(id: Int): String
}

class DefaultFileReader @Inject constructor(
    private val context: Context
) : FileReader {
    override fun text(id: Int): String {
        val input: InputStream = context.resources.openRawResource(id)
        return readTextFile(input)
    }

    private fun readTextFile(inputStream: InputStream): String {
        val outputStream = ByteArrayOutputStream()
        val buf = ByteArray(1024)
        var len: Int
        try {
            while (inputStream.read(buf).also { len = it } != -1) {
                outputStream.write(buf, 0, len)
            }
            outputStream.close()
            inputStream.close()
        } catch (e: IOException) {
        }
        return outputStream.toString()
    }
}