package com.droid.bookshelf.data.local

import android.app.Application
import android.util.Log

object AssetFileReader {

    fun getFileContent(application: Application, fileName: String): String? {
        try {
            application.assets.open(fileName).bufferedReader().use {
                return it.readText()
            }
        } catch (e: java.io.IOException) {
            Log.e("AssetFileReader", "Error reading file: $fileName", e)
            return null
        }

    }
}