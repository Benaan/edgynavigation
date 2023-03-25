package com.benaan.edgynavigation.brouter.gpx

fun replaceValueInXmlTag(xml: String, tag: String, value: String): String {
    val regex = "<$tag>(.*)</$tag>".toRegex(RegexOption.MULTILINE)
    return regex.replaceFirst(xml, "<$tag>$value</$tag>")
}