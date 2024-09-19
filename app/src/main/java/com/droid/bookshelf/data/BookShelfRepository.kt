package com.droid.bookshelf.data

import android.app.Application
import com.droid.bookshelf.data.models.Book

interface BookShelfRepository {

    fun getCountries(application: Application): List<String>?

    suspend fun getCurrentCountry():String?

    fun getBooks(application: Application): List<Book>?
}