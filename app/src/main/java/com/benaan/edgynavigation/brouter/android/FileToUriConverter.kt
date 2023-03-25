package com.benaan.edgynavigation.brouter.android

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

class FileToUriConverter(private val context: Context) {
    fun getUri(file: File): Uri? = FileProvider.getUriForFile(
        context, context.applicationContext.packageName + ".provider", file
    )
}