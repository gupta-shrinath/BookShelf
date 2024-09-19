package com.droid.bookshelf.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.droid.bookshelf.BookShelfApplication
import com.droid.bookshelf.BookShelfApplication.Companion.application
import com.droid.bookshelf.data.local.database.BookShelfDatabase
import com.droid.bookshelf.data.local.database.entities.User
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class BookShelfLocalDataSource {

    private val database = Room.databaseBuilder(
        application,
        BookShelfDatabase::class.java, BookShelfDatabase.DATABASE_NAME
    ).build()

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "book_shelf")
    val USER_ID =  stringPreferencesKey("user_id")

    fun readAssetFile(fileName: String): String? {
        return AssetFileReader.getFileContent(application, fileName)
    }

    suspend fun createUser(user: User) {
        database.userDao().insertUser(user)
    }

    suspend fun getUser(email:String, password: String) = database.userDao().getUser(email,password)

    suspend fun saveUserId(userId: String) {

        application.dataStore.edit { preferences ->
            preferences[USER_ID] = userId
        }
        Log.d("TAG", "saveUserId: ${userId}")
    }

    suspend fun getUserId():String {
       return application.dataStore.data.map { preferences ->
            preferences[USER_ID] ?: ""
        }.first()
    }
}