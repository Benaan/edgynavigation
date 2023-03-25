package com.benaan.edgynavigation

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.benaan.edgynavigation.brouter.android.FileToUriConverter
import com.benaan.edgynavigation.brouter.android.IntentLauncher
import com.benaan.edgynavigation.brouter.android.MapFragmentCloser
import com.benaan.edgynavigation.brouter.android.NewFileGenerator
import com.benaan.edgynavigation.brouter.map.BrouterWebView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MapFragment : Fragment(R.layout.map_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val url = requireArguments().getString("url")
        if (url.isNullOrBlank()) {
            return
        }
        val title = requireArguments().getString("title")
        val webView = view.findViewById<View>(R.id.webview) as WebView
        val button = view.findViewById<View>(R.id.startButton) as FloatingActionButton

        val context = this.activity ?: return

        val fileToUriConverter = FileToUriConverter(context)
        val newFileGenerator = NewFileGenerator(context.filesDir)
        val intentLauncher = IntentLauncher(context, fileToUriConverter, newFileGenerator)
        val mapFragment = MapFragmentCloser(this.parentFragmentManager, this)
        val brouterWebView =
            BrouterWebView(button, webView, intentLauncher, title, mapFragment)

        brouterWebView.setUpWebView(url)
    }


}