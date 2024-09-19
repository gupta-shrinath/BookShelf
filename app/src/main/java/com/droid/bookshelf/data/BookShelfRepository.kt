package com.droid.bookshelf.data

import android.app.Application
import com.droid.bookshelf.data.local.database.entities.User
import com.droid.bookshelf.data.models.Book

interface BookShelfRepository {

    suspend fun getCountries(): List<String>?

    suspend fun getCurrentCountry(): String?

    suspend fun getBooks(): List<Book>?

    suspend fun createUser(email: String, password: String, country: String): Boolean

    suspend fun getUser(email: String, password: String): User?

    suspend fun saveUserId(userId: String)

    suspend fun getUserId(): String
}