package com.benaan.edgynavigation.brouter

import com.benaan.edgynavigation.brouter.map.Coordinate
import com.benaan.edgynavigation.brouter.map.getZoomLevel
import java.net.URLEncoder

fun getBRouterUrlEditUrl(coordinates: Array<Coordinate>): String {
    val latCenter = coordinates.map { it.lat }.average()
    val lngCenter = coordinates.map { it.lng }.average()
    val zoomLevel = getZoomLevel(coordinates, 1.5)
    return "https://brouter.de/brouter-web/#map=$zoomLevel/$latCenter/$lngCenter/standard&lonlats=" + flattenCoordinates(
        coordinates,
        ";"
    )
}

fun getBRouterUrlGpxUrl(coordinates: Array<Coordinate>, title: String): String {
    val coordinatesString = flattenCoordinates(coordinates, "|")
    return "https://brouter.de/brouter?lonlats=$coordinatesString&profile=trekking&alternativeidx=0&format=gpx&trackname=" + URLEncoder.encode(
        title,
        "utf-8"
    )
}

fun flattenCoordinates(coordinates: Array<Coordinate>, separator: String): String {
    return coordinates.joinToString(separator = separator) { it.lng.toString() + "," + it.lat }
}