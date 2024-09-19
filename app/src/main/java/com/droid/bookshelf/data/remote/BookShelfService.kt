package com.droid.bookshelf.data.remote

import com.droid.bookshelf.data.models.Book
import com.droid.bookshelf.data.models.Country
import com.droid.bookshelf.data.models.LocationInfo
import retrofit2.Response
import retrofit2.http.GET

interface BookShelfService {

    @GET("http://ip-api.com/json")
    suspend fun getLocation(): Response<LocationInfo>

    @GET("https://jsonkeeper.com/b/IU1K")
    suspend fun getCountries():Response<List<Country>>

    @GET("https://jsonkeeper.com/b/CNGI")
    suspend fun getBooks():Response<List<Book>>
}
