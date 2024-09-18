package com.droid.bookshelf.data.models

import com.squareup.moshi.Json

data class LocationInfo(
    @Json(name = "status") val status: String,
    @Json(name = "country") val country: String,
    @Json(name = "countryCode") val countryCode: String,
    @Json(name = "region") val region: String,
    @Json(name = "regionName") val regionName: String,
    @Json(name = "city") val city: String,
    @Json(name = "zip") val zip: String,
    @Json(name = "lat") val latitude: Double,
    @Json(name = "lon") val longitude: Double,
    @Json(name = "timezone") val timezone: String,
    @Json(name = "isp") val isp: String,
    @Json(name = "org") val organization: String,
    @Json(name = "as") val autonomousSystem: String,
    @Json(name = "query") val queryIp: String
)
