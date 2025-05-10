package com.example.intellireview_research_paper.util

import android.content.Context
import android.net.Uri
import java.io.File

object FileUtils {
    fun getFileFromUri(context: Context, uri: Uri): File? {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val tempFile = File.createTempFile("profile_image", ".jpg", context.cacheDir)
        tempFile.outputStream().use { output ->
            inputStream.copyTo(output)
        }
        return tempFile
    }
}
