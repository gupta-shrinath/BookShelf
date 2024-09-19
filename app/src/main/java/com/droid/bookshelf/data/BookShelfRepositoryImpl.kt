package com.droid.bookshelf.data

import android.app.Application
import com.droid.bookshelf.BookShelfApplication
import com.droid.bookshelf.BookShelfApplication.Companion.application
import com.droid.bookshelf.data.local.AssetFileReader
import com.droid.bookshelf.data.local.BookShelfLocalDataSource
import com.droid.bookshelf.data.local.database.entities.User
import com.droid.bookshelf.data.models.Book
import com.droid.bookshelf.data.models.Country
import com.droid.bookshelf.data.remote.BookShelfNetworkDataSource
import com.droid.bookshelf.utils.toSHA256Hash
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class BookShelfRepositoryImpl : BookShelfRepository {

    private val localDataSource = BookShelfLocalDataSource()
    private val networkDataSource = BookShelfNetworkDataSource()
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    override suspend fun getCountries(): List<String>? {
//        val countriesJsonString =
//            localDataSource.readAssetFile("countries.json") ?: return null
//        try {
//            val listType = Types.newParameterizedType(List::class.java, Country::class.java)
//            val adapter = moshi.adapter<List<Country>>(listType)
//            val countries = adapter.fromJson(countriesJsonString)
//            return countries?.map { it.country }
//        } catch (e: java.io.IOException) {
//            return null
//        } catch (e: JsonDataException) {
//            return null
//        } catch (e: Exception) {
//            return null
//        }
        return networkDataSource.getCountries()?.map { it.country }
    }

    override suspend fun getCurrentCountry(): String? {
        return networkDataSource.getCurrentLocation()?.country
    }

    override suspend fun getBooks(): List<Book>? {
//        val booksJsonString =
//            localDataSource.readAssetFile("books.json") ?: return null
//        try {
//            val listType = Types.newParameterizedType(List::class.java, Book::class.java)
//            val adapter = moshi.adapter<List<Book>>(listType)
//            return adapter.fromJson(booksJsonString)
//        } catch (e: java.io.IOException) {
//            return null
//        } catch (e: JsonDataException) {
//            return null
//        } catch (e: Exception) {
//            return null
//        }
        val localBooks = localDataSource.getAllBooks()
        if (localBooks == null) {
            val books = networkDataSource.getBooks() ?: return null
            localDataSource.insertBooks(books)
            return books
        } else {
            return localDataSource.getAllBooks()
        }
    }

    override suspend fun createUser(email: String, password: String, country: String):Boolean {
        val user = User(0, email, password.toSHA256Hash(), country)
        return localDataSource.createUser(user)
    }

    override suspend fun getUser(email: String, password: String): User? {
        return try {
            localDataSource.getUser(email, password.toSHA256Hash())
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun saveUserId(userId: String) {
        localDataSource.saveUserId(userId)
    }

    override suspend fun getUserId() = localDataSource.getUserId()

    override suspend fun getUserLike(bookId: String):Boolean {
        val userId = localDataSource.getUserId()
        return localDataSource.getLikeForBook(bookId, userId)
    }

    override suspend fun likeBook(bookId: String) {
        val userId = localDataSource.getUserId()
        localDataSource.insertLikeForBook(bookId, userId)
    }

    override suspend fun unLikeBook(bookId: String) {
        val userId = localDataSource.getUserId()
        localDataSource.deleteLikeForBook(bookId, userId)
    }
}