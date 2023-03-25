package com.benaan.edgynavigation.brouter.gpx

import com.benaan.edgynavigation.brouter.map.Coordinate

fun decodePolyline(polyline: String): List<Coordinate> {
    val points = mutableListOf<Coordinate>()
    var index = 0
    var lat = 0.0
    var lng = 0.0
    while (index < polyline.length) {
        var b: Int
        var shift = 0
        var result = 0
        do {
            b = polyline[index++].code - 63
            result = result or (b and 0x1f shl shift)
            shift += 5
        } while (b >= 0x20)
        val dlat = if ((result and 1) != 0) -(result shr 1) else (result shr 1)
        lat += dlat.toDouble() / 1e5
        shift = 0
        result = 0
        do {
            b = polyline[index++].code - 63
            result = result or (b and 0x1f shl shift)
            shift += 5
        } while (b >= 0x20)
        val dlng = if ((result and 1) != 0) -(result shr 1) else (result shr 1)
        lng += dlng.toDouble() / 1e5
        points.add(Coordinate(lat, lng))
    }
    return points
}

fun createGpx(coordinates: List<Coordinate>, name: String): String {
    buildString { }
    var gpx = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
    gpx += "<gpx xmlns=\"http://www.topografix.com/GPX/1/1\" version=\"1.1\" creator=\"MyScript\">"
    gpx += "<trk>"
    gpx += "<name>$name</name>"
    gpx += "<trkseg>"
    for (point in coordinates) {
        gpx += "<trkpt lat=\"${point.lat}\" lon=\"${point.lng}\"></trkpt>"
    }
    gpx += "</trkseg>"
    gpx += "</trk>"
    gpx += "</gpx>"
    return gpx
}
