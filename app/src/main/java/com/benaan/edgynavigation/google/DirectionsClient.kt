package com.benaan.edgynavigation.google

//
//class DirectionsClient(private val apiKey: String) {
//    fun getPolyline(startCoordinate: Coordinate, toAddress: String): String? {
//        val data: String = getData(startCoordinate, toAddress) ?: return null
//        return getCoordinatesFromJson(data)
//    }
//
//    private fun getPolylineFromJson(data: String): String? {
//        try {
//            val json = JSONObject(data)
//            val classArray = json.getJSONArray("routes")
//            val resultJson = classArray.getJSONObject(0)
//            val legsArray = resultJson.getJSONArray("legs")
//            val legJson = legsArray.getJSONObject(0)
//            val geometryJson = resultJson.getJSONObject("geometry")
//            val locationJson = geometryJson.getJSONObject("location")
//            val latitude = locationJson.getDouble("lat")
//            val longitude = locationJson.getDouble("lng")
//            return Coordinate(latitude, longitude)
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//        return null
//    }
//
//    private fun getData(startCoordinate: Coordinate, toAddress: String): String? {
//        val lat = startCoordinate.lat
//        val lng = startCoordinate.lng
//        return try {
//            URL("https://maps.googleapis.com/maps/api/directions/json?origin=$lat,$lng&destination=$toAddress&key=$apiKey").readText()
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }
//}