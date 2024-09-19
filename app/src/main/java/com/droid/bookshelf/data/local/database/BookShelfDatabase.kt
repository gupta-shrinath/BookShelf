package com.droid.bookshelf.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.droid.bookshelf.data.local.database.daos.BookDao
import com.droid.bookshelf.data.local.database.daos.LikeDao
import com.droid.bookshelf.data.local.database.daos.UserDao
import com.droid.bookshelf.data.local.database.entities.Like
import com.droid.bookshelf.data.local.database.entities.User
import com.droid.bookshelf.data.models.Book

@Database(entities = [User::class,Book::class,Like::class], version = 1)
abstract class BookShelfDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun bookDao(): BookDao
    abstract fun likeDao(): LikeDao

    companion object {
         const val DATABASE_NAME = "book_shelf_database"
    }
}
