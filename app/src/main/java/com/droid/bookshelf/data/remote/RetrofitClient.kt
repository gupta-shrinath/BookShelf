package com.droid.bookshelf.data.remote

import com.droid.bookshelf.BookShelfApplication
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {

    private var cacheSize: Int = 10 * 1024 * 1024
    private var cache: Cache = Cache(BookShelfApplication.application.cacheDir, cacheSize.toLong())

    private const val TIMEOUT = 30L
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(CacheInterceptor())
        .cache(cache)
        // Add due to jsonkeeper.com endpoint
        .hostnameVerifier { _, _ -> true }
        .build()


    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val instance: Retrofit =
        Retrofit.Builder().addConverterFactory(
            MoshiConverterFactory.create(moshi)
        ).client(client).baseUrl("https://www.google.com").build()


}