package com.echo.core.utils.files

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

fun Uri.toMultipartBody(
    context: Context,
    partName: String = ""
): MultipartBody.Part {

    val contentResolver = context.contentResolver
    val mimeType = contentResolver.getType(this) ?: "image/jpeg"
    val fileName = "${partName}_${System.currentTimeMillis() / 1000}.jpg"

    val inputStream = contentResolver.openInputStream(this)
    val bytes = inputStream?.readBytes() ?: ByteArray(0)
    inputStream?.close()

    val requsetBody = bytes.toRequestBody(mimeType.toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(partName, fileName, requsetBody)
}