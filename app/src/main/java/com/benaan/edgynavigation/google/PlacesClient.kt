package com.benaan.edgynavigation.google

import android.content.SharedPreferences
import com.benaan.edgynavigation.brouter.map.Coordinate
import org.json.JSONException
import org.json.JSONObject
import java.net.URL

class PlacesClient(private val settings: SharedPreferences) {
    fun getCoordinatesForAddress(address: String): Coordinate? {
        val data: String = getData(address) ?: return null
        return getCoordinatesFromJson(data)
    }

    private fun getCoordinatesFromJson(data: String): Coordinate? {
        try {
            val json = JSONObject(data)
            val classArray = json.getJSONArray("results")
            val resultJson = classArray.getJSONObject(0)
            val geometryJson = resultJson.getJSONObject("geometry")
            val locationJson = geometryJson.getJSONObject("location")
            val latitude = locationJson.getDouble("lat")
            val longitude = locationJson.getDouble("lng")
            return Coordinate(latitude, longitude)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    private fun getData(address: String): String? {
        return try {
            val apiKey = settings.getString("apiKey", "")
            URL("https://maps.googleapis.com/maps/api/geocode/json?key=$apiKey&address=$address").readText()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

