package com.droid.bookshelf.data.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.droid.bookshelf.data.local.database.entities.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE email =:email AND password = :password")
    suspend fun getUser(email: String, password: String): User

}