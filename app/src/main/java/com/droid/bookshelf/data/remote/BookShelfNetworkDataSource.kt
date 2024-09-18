package com.droid.bookshelf.data.remote

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
}