package com.benaan.edgynavigation.google

import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import com.benaan.edgynavigation.brouter.android.IntentLauncher
import com.benaan.edgynavigation.brouter.android.MapFragmentOpener
import com.benaan.edgynavigation.brouter.getBRouterUrlEditUrl
import com.benaan.edgynavigation.brouter.getBRouterUrlGpxUrl
import com.benaan.edgynavigation.brouter.map.Coordinate
import com.benaan.edgynavigation.util.InvalidDestinationMessage
import com.benaan.edgynavigation.util.getFirstLine
import com.benaan.edgynavigation.util.removeNonAlphaNumeric
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MapShareHandler(
    private val mapShareCoordinatesExtractor: MapShareCoordinatesExtractor,
    private val intentLauncher: IntentLauncher,
    private val invalidDestinationMessage: InvalidDestinationMessage,
    private val settings: SharedPreferences,
    private val mapFragmentOpener: MapFragmentOpener,
) {

    private val tag = "MapShareHandler"

    fun handleMapShare(intent: Intent) {
        val action = intent.action
        val type = intent.type
        if (action == Intent.ACTION_SEND && type == "text/plain") {
            val text = intent.getStringExtra(Intent.EXTRA_TEXT)
            if (text == null) {
                invalidDestinationMessage.show()
                Log.d(tag, "No text received")
                return
            }
            getRoute(text)
        }
    }

    private fun getRoute(sharedText: String) {
        Log.d(tag, sharedText)
        CoroutineScope(Dispatchers.IO).launch {
            val coordinates =
                mapShareCoordinatesExtractor.getCoordinates(sharedText)
            if (coordinates == null) {
                invalidDestinationMessage.show()
                return@launch
            }
            val title = removeNonAlphaNumeric(getFirstLine(sharedText)).trim()
            if (settings.getBoolean("editRoute", true)) {
                openBrouterMap(coordinates, title)
            } else {
                openDirectly(coordinates, title)
            }
        }
    }

    private suspend fun openBrouterMap(coordinates: Array<Coordinate>, title: String) {
        val url = getBRouterUrlEditUrl(coordinates)
        withContext(Dispatchers.Main) {
            Log.d(tag, url)
            mapFragmentOpener.openMap(url, title)
        }
    }

    private fun openDirectly(coordinates: Array<Coordinate>, title: String) {
        val url = getBRouterUrlGpxUrl(coordinates, title)
        val data = URL(url).readText()
        intentLauncher.openDataIntent(data)
    }
}