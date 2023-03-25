package com.benaan.edgynavigation.brouter.gpx

import android.util.Base64
import com.benaan.edgynavigation.brouter.android.getBase64DataFromUrl

fun getGpxXmlFromBase64Url(url: String, title: String?): String {
    val data = getBase64DataFromUrl(url)
    val xml = String(Base64.decode(data, Base64.DEFAULT))
    if (!title.isNullOrBlank()) {
        return replaceValueInXmlTag(xml, "name", title)
    }
    return xml
}