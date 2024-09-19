package com.droid.bookshelf.data.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.droid.bookshelf.data.models.Book

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books:List<Book>)

    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<Book>
}