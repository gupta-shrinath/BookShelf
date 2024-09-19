package com.droid.bookshelf.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.droid.bookshelf.data.local.database.daos.UserDao
import com.droid.bookshelf.data.local.database.entities.User

@Database(entities = [User::class], version = 1)
abstract class BookShelfDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
         const val DATABASE_NAME = "book_shelf_database"
    }
}
