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
import com.droid.bookshelf.data.local.database.entities.Like
import com.droid.bookshelf.data.local.database.entities.User
import com.droid.bookshelf.data.models.Book
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val TAG = "LocalDataSource"

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

    suspend fun createUser(user: User):Boolean {
        try {
            database.userDao().insertUser(user)
            return true
        } catch (e:Exception) {
            Log.e(TAG, "createUser failed")
            return false
        }

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

    suspend fun getAllBooks():List<Book>? {
        return try {
            database.bookDao().getAllBooks().takeIf { it.isNotEmpty() }
        } catch (e:Exception)  {
            return null
        }

    }

    suspend fun insertBooks(books:List<Book>) {
         try {
            database.bookDao().insertBooks(books)
        } catch (e:Exception)  {
             Log.e(TAG, "insertBooks failed")
         }
    }

        suspend fun insertLikeForBook(bookId:String, userId:String) {
            try {
                Log.d(TAG, "insertLikeForBook:$bookId$userId")
                database.likeDao().insertLike(Like(0,bookId,userId))
            } catch (e:Exception) {
                Log.e(TAG, "insertLikeForBook failed")
            }
        }

        suspend fun getLikeForBook(bookId:String, userId:String): Boolean {
            return  try {
                val like = database.likeDao().getUserLikeForBook(bookId,userId)
                Log.d(TAG, "getLikeForBook:$bookId$userId$like")
                 like != null
            } catch (e:Exception) {
                Log.e(TAG, "getLikeForBook failed")
                 false
            }
        }

}