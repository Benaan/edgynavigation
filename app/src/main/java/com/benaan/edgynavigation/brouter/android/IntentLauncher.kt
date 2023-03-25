package com.benaan.edgynavigation.brouter.android

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import java.io.File

class IntentLauncher(
    private val activity: Context,
    private val fileToUriConverter: FileToUriConverter,
    private val newFileGenerator: NewFileGenerator,
) {
    private val tag = "IntentLauncher"
    fun openFileIntent(file: File) {
        val uri = fileToUriConverter.getUri(file)
        if (uri == null) {
            Log.e(tag, "File couldn't be converted to Uri")
            return
        }
        openUriIntent(uri)
    }

    fun openDataIntent(data: String) {
        val file = newFileGenerator.getNewFile(data)
        openFileIntent(file)
    }

    fun openUriIntent(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "application/gpx+xml")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        activity.startActivity(intent)
    }
}
