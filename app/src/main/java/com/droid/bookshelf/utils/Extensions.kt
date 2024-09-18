package com.droid.bookshelf.utils

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