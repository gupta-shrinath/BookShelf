package com.droid.bookshelf.data

import android.app.Application

interface BookShelfRepository {

    fun getCountries(application: Application): List<String>?

    suspend fun getCurrentCountry():String?
}