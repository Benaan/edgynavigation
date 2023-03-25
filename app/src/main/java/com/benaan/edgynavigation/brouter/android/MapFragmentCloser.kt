package com.benaan.edgynavigation.brouter.android

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

class MapFragmentCloser(
    private val fragmentManager: FragmentManager,
    private val fragment: Fragment
) {
    fun closeMap() {
        fragmentManager.commit {
            remove(fragment)
        }
    }
}