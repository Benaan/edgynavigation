package com.benaan.edgynavigation.google

import android.util.Log
import com.benaan.edgynavigation.brouter.android.LocationProvider
import com.benaan.edgynavigation.brouter.map.Coordinate


class MapShareCoordinatesExtractor(
    private val locationProvider: LocationProvider,
    private val placesClient: PlacesClient
) {
    private val tag = "MapShareCoordinatesExtractor"

    suspend fun getCoordinates(sharedText: String): Array<Coordinate>? {
        Log.i(tag, "SharedText: $sharedText")
        val url = getUrlFromText(sharedText)
        if (url == null) {
            Log.i(tag, "Couldn't get url from sharedText")
            return null
        }
        var destinationCoordinates = getDestinationCoordinates(url)

        if (destinationCoordinates == null) {
            val unshortenedUrl = unshortenUrl(url)

            Log.i(tag, "Unshortened url to $unshortenedUrl")
            destinationCoordinates = getDestinationCoordinates(unshortenedUrl)
        }

        if (destinationCoordinates == null) {
            Log.i(tag, "couldn't get destination coordinates")
            return null
        }

        val startCoordinates = locationProvider.getCurrentLocation()
        if (startCoordinates == null) {
            Log.i(tag, "couldn't get current location")
            return null
        }
        return arrayOf(startCoordinates, destinationCoordinates)
    }

    private fun getDestinationCoordinates(url: String): Coordinate? {

        val coordinatesFromData = getCoordinatesFromData(url)
        if (coordinatesFromData != null) {
            return coordinatesFromData
        }
        Log.i(tag, "No coordinates in data")

        val coordinatesFromPlace = getCoordinatesFromPlace(url)
        if (coordinatesFromPlace != null) {
            return coordinatesFromPlace
        }
        Log.i(tag, "No coordinates in place")

        val coordinatesFromDirections = getCoordinatesFromDirections(url)
        if (coordinatesFromDirections != null) {
            return coordinatesFromDirections
        }
        Log.i(tag, "No coordinates from directions")

        val address = getAddressFromUrl(url)
        if (address == null) {
            Log.i(tag, "couldn't get address from url $url")
            return null
        }

        return placesClient.getCoordinatesForAddress(address)
    }
}