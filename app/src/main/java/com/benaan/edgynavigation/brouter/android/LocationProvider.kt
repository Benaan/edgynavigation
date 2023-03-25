package com.benaan.edgynavigation.brouter.android

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationRequest
import android.util.Log
import androidx.core.app.ActivityCompat
import com.benaan.edgynavigation.brouter.map.Coordinate
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await

class LocationProvider(context: Context) {
    private val context: Context
    private val fusedLocationClient: FusedLocationProviderClient
    private val tag = "LocationProvider"

    init {
        this.context = context.applicationContext
        this.fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(context.applicationContext)
    }

    suspend fun getCurrentLocation(): Coordinate? {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.i(tag, "No location permissions")
            return null
        }
        val cancellationTokenSource = CancellationTokenSource()
        val result = fusedLocationClient.getCurrentLocation(
            LocationRequest.QUALITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).await()
        if (result == null) {
            Log.i(tag, "Couldn't get location")
            return null
        }
        return Coordinate(result.latitude, result.longitude)
    }
}