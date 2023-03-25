package com.benaan.edgynavigation.brouter.map

import android.annotation.SuppressLint
import android.webkit.WebSettings
import android.webkit.WebView
import com.benaan.edgynavigation.brouter.android.IntentLauncher
import com.benaan.edgynavigation.brouter.android.MapFragmentCloser
import com.benaan.edgynavigation.brouter.gpx.getGpxXmlFromBase64Url
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BrouterWebView(
    private val button: FloatingActionButton,
    private val webView: WebView,
    private val intentLauncher: IntentLauncher,
    private val title: String?,
    private val mapCloser: MapFragmentCloser
) {
    @SuppressLint("SetJavaScriptEnabled")
    fun setUpWebView(url: String) {
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        webView.setDownloadListener { downloadurl, _, _, _, _ ->
            val data = getGpxXmlFromBase64Url(downloadurl, title)
            intentLauncher.openDataIntent(data)
            mapCloser.closeMap()
        }

        webView.loadUrl(url)

        button.setOnClickListener {
            webView.evaluateJavascript("L.DomUtil.get('submitExport').click()", null)
        }
    }
}