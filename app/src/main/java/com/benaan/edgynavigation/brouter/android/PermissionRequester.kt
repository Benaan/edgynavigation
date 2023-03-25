package com.benaan.edgynavigation.brouter.android

import android.Manifest
import android.app.Activity
import androidx.core.app.ActivityCompat

class PermissionRequester(private val context: Activity) {

    fun requestPermission() {
        ActivityCompat.requestPermissions(
            context, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            ), 1
        )
    }
}