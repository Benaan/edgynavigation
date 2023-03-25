package com.benaan.edgynavigation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.benaan.edgynavigation.brouter.android.*
import com.benaan.edgynavigation.google.MapShareCoordinatesExtractor
import com.benaan.edgynavigation.google.MapShareHandler
import com.benaan.edgynavigation.google.PlacesClient
import com.benaan.edgynavigation.util.InvalidDestinationMessage

class MainActivity : AppCompatActivity() {

    private lateinit var mapShareHandler: MapShareHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        PermissionRequester(this).requestPermission()
        val invalidDestinationMessage = InvalidDestinationMessage(this)
        val fileToUriConverter = FileToUriConverter(this)
        val newFileGenerator = NewFileGenerator(this.filesDir)
        val intentLauncher = IntentLauncher(this, fileToUriConverter, newFileGenerator)
        val placesClient = PlacesClient(sharedPreferences)
        val mapShareCoordinatesExtractor =
            MapShareCoordinatesExtractor(LocationProvider(this), placesClient)

        val mapFragmentOpener = MapFragmentOpener(supportFragmentManager)

        mapShareHandler = MapShareHandler(
            mapShareCoordinatesExtractor,
            intentLauncher,
            invalidDestinationMessage,
            sharedPreferences,
            mapFragmentOpener
        )

        mapShareHandler.handleMapShare(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            mapShareHandler.handleMapShare(intent)
        }
    }
}