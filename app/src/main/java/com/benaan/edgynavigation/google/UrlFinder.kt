package com.benaan.edgynavigation.google

import java.util.regex.Pattern

fun getUrlFromText(text: String): String? {
    val urlRegex = "(https?://\\S+)"
    val pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE)
    val urlMatcher = pattern.matcher(text)
    while (urlMatcher.find()) {
        return text.substring(
            urlMatcher.start(0),
            urlMatcher.end(0)
        )
    }
    return null
}