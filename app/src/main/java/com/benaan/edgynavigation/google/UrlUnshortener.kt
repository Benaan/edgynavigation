package com.benaan.edgynavigation.google


import java.net.HttpURLConnection
import java.net.URL

fun unshortenUrl(shortUrl: String): String {
    return try {
        val con = URL(shortUrl).openConnection() as HttpURLConnection
        con.instanceFollowRedirects = false
        con.connect()
        con.inputStream
        if (con.responseCode == HttpURLConnection.HTTP_MOVED_PERM || con.responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
            con.getHeaderField("Location")
        } else shortUrl
    } catch (e: java.lang.Exception) {
        shortUrl
    }
}
