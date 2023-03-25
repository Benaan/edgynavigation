package com.benaan.edgynavigation.brouter.android

import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

fun getBase64DataFromUrl(url: String): String {
    return url.replace("data:application/gpx+xml;charset=utf-8;base64,", "")
}

fun saveFile(file: File, data: String) {
    val fileOutputStream = FileOutputStream(file)
    fileOutputStream.write(data.encodeToByteArray())
    fileOutputStream.close()
}

fun generateRandomFilename(extension: String): String {
    val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val random = Random.nextInt(1000000, 9999999)
    return "${timestamp}_$random.$extension"
}