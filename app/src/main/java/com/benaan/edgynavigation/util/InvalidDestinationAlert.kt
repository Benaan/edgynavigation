package com.benaan.edgynavigation.util

import android.content.Context
import android.widget.Toast
import com.benaan.edgynavigation.R


class InvalidDestinationMessage(private val context: Context) {
    fun show() {
        val toast = Toast.makeText(context, R.string.invalid_destination, Toast.LENGTH_LONG)
        toast.show()
    }
}