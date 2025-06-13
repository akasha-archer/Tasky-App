package com.example.taskyapplication.agenda.items.event.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.inject.Inject
import androidx.core.graphics.scale

class ImageMultiPartProvider @Inject constructor() {

    fun createMultipartParts(context: Context, imageUris: List<Uri>): List<MultipartBody.Part> {
        val parts = mutableListOf<MultipartBody.Part>()
        imageUris.forEachIndexed { index, uri ->
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val compressedImage = compressUploadedImage(context, uri)
                if (compressedImage != null) {
                    val requestFile = compressedImage.toRequestBody(
                        "image/jpeg".toMediaTypeOrNull()
                    )
                    val part =
                        MultipartBody.Part.createFormData("photo", "photo$index.jpg", requestFile)
                    parts.add(part)
                }
            }
        }
        return parts
    }

    private fun compressUploadedImage(context: Context, uri: Uri): ByteArray? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val maxWidth = 1024
            val maxHeight = 1024
            val ratio = minOf(maxWidth.toFloat() / bitmap.width, maxHeight.toFloat() / bitmap.height)
            val newWidth = (bitmap.width * ratio).toInt()
            val newHeight = (bitmap.height * ratio).toInt()
            val resizedBitmap = bitmap.scale(newWidth, newHeight)
            val outputStream = ByteArrayOutputStream()
            var quality = 85
            do {
                outputStream.reset()
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                quality -= 5
            } while (outputStream.size() > 1024 * 1024 && quality > 10)
            Log.d("ImageProvider","Compressed image to ${outputStream.size()} bytes")
            outputStream.toByteArray()
        } catch (e: Exception) {
            Log.d("ImageProvider", "Compression error occurred: ${e.message}")
            null
        }
    }
}