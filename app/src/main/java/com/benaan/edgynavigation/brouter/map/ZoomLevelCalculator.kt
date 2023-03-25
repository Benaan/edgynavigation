package com.benaan.edgynavigation.brouter.map

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.Size
import kotlin.math.*

fun getZoomLevel(coordinates: Array<Coordinate>, paddingFactor: Double): Int {

    val maxLat = coordinates.maxOf { it.lat }
    val maxLng = coordinates.maxOf { it.lng }

    val minLat = coordinates.minOf { it.lat }
    val minLng = coordinates.minOf { it.lng }

    val ry1 = ln((sin(degToRad(minLat)) + 1) / cos(degToRad(minLat)))
    val ry2 = ln((sin(degToRad(maxLat)) + 1) / cos(degToRad(maxLat)))
    val ryc = (ry1 + ry2) / 2
    val centerY = radToDeg(atan(sinh(ryc)))

    val viewPort = getViewPort()

    val resolutionHorizontal = (maxLng - minLng) / viewPort.width

    val vy0 = ln(tan(PI * (0.25 + centerY / 360)))
    val vy1 = ln(tan(PI * (0.25 + maxLat / 360)))
    val viewHeightHalf = viewPort.height / 2.0
    val zoomFactorPowered = viewHeightHalf / (40.7436654315252 * (vy1 - vy0))
    val resolutionVertical = 360.0 / (zoomFactorPowered * 256)

    val resolution = max(resolutionHorizontal, resolutionVertical) * paddingFactor
    val zoom = ln(360 / (resolution * 256)) / ln(2.0)

    return floor(zoom).roundToInt()
}

private fun degToRad(degrees: Double) = degrees * PI / 180.0
private fun radToDeg(radians: Double) = radians * 180.0 / PI


fun getViewPort(): Size {
    val displayMetrics: DisplayMetrics = Resources.getSystem().displayMetrics
    val screenHeight: Int = displayMetrics.heightPixels
    val screenWidth: Int = displayMetrics.widthPixels
    val density: Float = Resources.getSystem().displayMetrics.density
    val screenHDI = screenHeight / density
    val screenWDI = screenWidth / density

    return Size(ceil(screenWDI).roundToInt(), ceil(screenHDI).roundToInt())
}