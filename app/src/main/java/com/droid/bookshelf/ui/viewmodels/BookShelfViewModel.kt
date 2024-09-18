package com.droid.bookshelf.ui.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import com.droid.bookshelf.data.BookShelfRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BookShelfViewModel : ViewModel() {

    private val repository = BookShelfRepositoryImpl()

    fun getCountries(application: Application): Flow<Async<List<String>>> = flow {
        try {
            emit(Async.Loading)
            val currentCountry = repository.getCurrentCountry()
            val countries = repository.getCountries(application)
            if (!currentCountry.isNullOrBlank() && countries != null) {
                val sortedCountries = countries.toMutableList()
                sortedCountries.sort()
                sortedCountries.remove(currentCountry)
                sortedCountries.add(0,currentCountry)
                emit(Async.Success(sortedCountries))
            }
            else if (countries != null) {
                emit(Async.Success(countries))
            }
            else {
                emit(Async.Error("Error fetching countries")) // Emit error state
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