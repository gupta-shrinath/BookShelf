package com.droid.bookshelf.data.models

import com.squareup.moshi.Json

data class LocationInfo(
    val status: String,
    val country: String,
    val countryCode: String,
    val region: String,
    val regionName: String,
    val city: String,
    val zip: String,
    @Json(name = "lat") val latitude: Double,
    @Json(name = "lon") val longitude: Double,
    @Json(name = "timezone") val timezone: String,
    @Json(name = "isp") val isp: String,
    @Json(name = "org") val organization: String,
    @Json(name = "as") val autonomousSystem: String,
    @Json(name = "query") val queryIp: String
)
