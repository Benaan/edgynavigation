package com.benaan.edgynavigation.util

fun getFirstLine(input: String): String {
    val firstLineEnd = input.indexOfAny(charArrayOf('\n', '\r'))
    return if (firstLineEnd != -1) {
        input.substring(0, firstLineEnd)
    } else {
        input
    }
}
