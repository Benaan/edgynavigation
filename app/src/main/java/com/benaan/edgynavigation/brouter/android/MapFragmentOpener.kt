package com.benaan.edgynavigation.brouter.android

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.benaan.edgynavigation.MapFragment
import com.benaan.edgynavigation.R

class MapFragmentOpener(private val fragmentManager: FragmentManager) {
    fun openMap(url: String, title: String?) {
        val bundle = Bundle()
        bundle.putString("url", url)
        bundle.putString("title", title)
        fragmentManager.commit {
            setReorderingAllowed(true).add(R.id.fragment_container, MapFragment::class.java, bundle)
        }

    }
}