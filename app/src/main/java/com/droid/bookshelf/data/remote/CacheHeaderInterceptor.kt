package com.droid.bookshelf.data.remote

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        // Use CacheControl to specify caching behavior
        val cacheControl = CacheControl.Builder()
            .maxStale(1, TimeUnit.HOURS) // Allow stale responses for up to 1 hour
            .build()

        val newRequest = request.newBuilder()
            .header("Cache-Control", "max-age=3600") // Cache for 1 hour
            .cacheControl(cacheControl)
            .build()

        return chain.proceed(newRequest)
    }
}