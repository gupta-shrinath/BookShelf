package com.droid.bookshelf.data.remote

import com.droid.bookshelf.data.models.Book
import com.droid.bookshelf.data.models.Country
import com.droid.bookshelf.data.models.LocationInfo

class BookShelfNetworkDataSource {

    private val service: BookShelfService = RetrofitClient.instance.create(BookShelfService::class.java)

    suspend fun getCurrentLocation(): LocationInfo? {
        return try {
            val response = service.getLocation()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }

    }

    suspend fun getCountries(): List<Country>? {
        return try {
            val response = service.getCountries()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }

    }

    suspend fun getBooks(): List<Book>? {
        return try {
            val response = service.getBooks()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }

    }
}