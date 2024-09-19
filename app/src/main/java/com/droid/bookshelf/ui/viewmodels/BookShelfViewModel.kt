package com.droid.bookshelf.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.droid.bookshelf.BookShelfApplication.Companion.application
import com.droid.bookshelf.data.BookShelfRepositoryImpl
import com.droid.bookshelf.data.models.Book
import com.droid.bookshelf.utils.getYearFromTimestamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BookShelfViewModel : ViewModel() {

    private val repository = BookShelfRepositoryImpl()

    fun getCountries(): Flow<Async<List<String>>> = flow {
        try {
            emit(Async.Loading)
            val currentCountry = repository.getCurrentCountry()
            val countries = repository.getCountries(application)
            if (!currentCountry.isNullOrBlank() && countries != null) {
                val sortedCountries = countries.toMutableList()
                sortedCountries.sort()
                sortedCountries.remove(currentCountry)
                sortedCountries.add(0, currentCountry)
                emit(Async.Success(sortedCountries))
            } else if (countries != null) {
                emit(Async.Success(countries))
            } else {
                emit(Async.Error("Error fetching countries")) // Emit error state
            }
        } catch (e: Exception) {
            emit(Async.Error("An unexpected error occurred: ${e.message}"))
        }
    }

    fun getBooks(): Flow<Async<List<Book>>> = flow {
        try {
            emit(Async.Loading)
            val books = repository.getBooks(application)
                ?.sortedBy { it.publishedChapterDate.getYearFromTimestamp() }?.reversed()
            if (books == null) {
                emit(Async.Error("No valid books found"))
            } else {
                emit(Async.Success(books))
            }

        } catch (e: Exception) {
            emit(Async.Error("An unexpected error occurred: ${e.message}"))
        }
    }

}

sealed class Async<out T : Any> {
    object Loading : Async<Nothing>()

    data class Error(val errorMessage: String) : Async<Nothing>()

    data class Success<out T : Any>(val data: T) : Async<T>()
}