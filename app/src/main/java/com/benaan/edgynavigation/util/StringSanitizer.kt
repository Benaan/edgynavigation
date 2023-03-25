package com.benaan.edgynavigation.util

fun removeNonAlphaNumeric(input: String): String {
    val regex = Regex("[^A-Za-z\\d .,_+\\-()]")
    return regex.replace(input, "")
}