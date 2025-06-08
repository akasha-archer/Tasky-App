package com.example.taskyapplication.agenda.items.event.domain

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

class ImageMultiPartProvider @Inject constructor() {

    fun createMultipartParts(context: Context, imageUris: List<Uri>): List<MultipartBody.Part> {
        val parts = mutableListOf<MultipartBody.Part>()
        imageUris.forEachIndexed { index, uri ->
            // Use ContentResolver to get the file content
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val file = createTempFileFromInputStream(context, inputStream, "photo$index")
                val requestFile = file.readBytes().toRequestBody(
                    context.contentResolver.getType(uri)?.toMediaTypeOrNull()
                )

                // The part name is crucial for the server to identify the files.
                // A common convention is "files" or "images[]".
                val part = MultipartBody.Part.createFormData("files", file.name, requestFile)
                parts.add(part)
            }
        }
        return parts
    }

    // Helper function to copy content to a temporary file to handle all Uri types
    private fun createTempFileFromInputStream(context: Context, inputStream: InputStream, fileName: String): File {
        val tempFile = File(context.cacheDir, fileName)
        tempFile.createNewFile()
        FileOutputStream(tempFile).use { outputStream ->
            inputStream.copyTo(outputStream)
        }
        return tempFile
    }
}