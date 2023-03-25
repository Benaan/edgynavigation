package com.benaan.edgynavigation.google

import com.benaan.edgynavigation.brouter.map.Coordinate

fun getCoordinatesFromData(url: String): Coordinate? {
    return getCoordinatesFromRegex(url, "m2!.d([\\d.]+)!.d([\\d.]+)")
}

fun getCoordinatesFromPlace(url: String): Coordinate? {
    return getCoordinatesFromRegex(url, "place/([\\d.]+),([\\d.]+)/data")
}

fun getCoordinatesFromDirections(url: String): Coordinate? {
    return getCoordinatesFromRegex(url, "/dir/([\\d.]+),([\\d.]+)/[\\d.]+,[\\d.]+")
}

private fun getCoordinatesFromRegex(url: String, regexPattern: String): Coordinate? {
    val regex = Regex(regexPattern)
    val match = regex.find(url) ?: return null
    val lat = match.groupValues[1].toDoubleOrNull()
    val lng = match.groupValues[2].toDoubleOrNull()

    if (lat == null || lng == null) {
        return null
    }

    return Coordinate(lat, lng)
}

fun getAddressFromUrl(url: String): String? {
    val regex = Regex("place/(.+)/data=")
    val match = regex.find(url) ?: return null
    return match.groupValues[0]
}
