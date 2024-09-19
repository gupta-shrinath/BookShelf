package com.droid.bookshelf.utils

import java.security.MessageDigest
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Calendar

fun String.isValidPassword(): Boolean {
    val passwordRegex = Regex(
        pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&*(),])[A-Za-z0-9!@#\$%^&*(),]{8,}$"
    )
    return passwordRegex.matches(this)
}

fun String.isValidEmail(): Boolean {
    val emailRegex = Regex(
        pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    )
    return emailRegex.matches(this)
}

fun Long.getYearFromTimestamp(): Int {
    val calendar = Calendar.getInstance()
    // Convert the timestamp from seconds to milliseconds and set it in the calendar
    calendar.timeInMillis = this * 1000L
    return calendar.get(Calendar.YEAR)
}

@OptIn(ExperimentalStdlibApi::class)
fun String.toSHA256Hash(): String {
    val bytes = this.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    return digest.toHexString()
}