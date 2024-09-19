package com.droid.bookshelf.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSession


object RetrofitClient {

    private const val TIMEOUT = 30L
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        // Add due to jsonkeeper.com endpoint
        .hostnameVerifier { _, _ -> true }
        .build()

    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val instance: Retrofit =
        Retrofit.Builder().addConverterFactory(
            MoshiConverterFactory.create(moshi)
        ).client(client).baseUrl("https://www.google.com").build()


}