package com.droid.bookshelf.data

import android.app.Application
import com.droid.bookshelf.data.local.AssetFileReader
import com.droid.bookshelf.data.models.Country
import com.droid.bookshelf.data.remote.BookShelfNetworkDataSource
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class BookShelfRepositoryImpl: BookShelfRepository {

    val networkDataSource = BookShelfNetworkDataSource()

    override fun getCountries(application: Application): List<String>? {
       val countriesJsonString = AssetFileReader.getFileContent(application,"countries.json") ?: return null
        try {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val listType = Types.newParameterizedType(List::class.java, Country::class.java)
            val adapter = moshi.adapter<List<Country>>(listType)
            val countries = adapter.fromJson(countriesJsonString)
            return countries?.map { it.country }
        } catch (e: java.io.IOException) {
            return null
        } catch (e: JsonDataException) {
            return null
        }
        catch (e: Exception) {
            return null
        }
    }

    override suspend fun getCurrentCountry(): String? {
      return networkDataSource.getCurrentLocation()?.country
    }
}