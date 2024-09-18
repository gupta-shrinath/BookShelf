package com.droid.bookshelf.data.remote

import com.droid.bookshelf.data.models.LocationInfo
import retrofit2.Response
import retrofit2.http.GET

interface BookShelfService {

    @GET("http://ip-api.com/json")
    suspend fun getLocation(): Response<LocationInfo>
}
