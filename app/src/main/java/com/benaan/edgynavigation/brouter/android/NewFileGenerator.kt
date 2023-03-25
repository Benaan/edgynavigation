package com.benaan.edgynavigation.brouter.android

import java.io.File

class NewFileGenerator(private val filesDir: File) {
    fun getNewFile(data: String): File {
        val file = File(this.filesDir, generateRandomFilename("gpx"))
        saveFile(file, data)
        return file
    }
}