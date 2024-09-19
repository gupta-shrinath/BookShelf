package com.droid.bookshelf.data.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.droid.bookshelf.data.local.database.entities.Like

@Dao
interface LikeDao {

    @Insert
    suspend fun insertLike(like: Like)

    @Query("SELECT * FROM likes WHERE bookId = :bookId AND userId = :userId")
    suspend fun getUserLikeForBook(bookId: String, userId: String): Like?

    @Query("DELETE FROM likes WHERE bookId = :bookId AND userId = :userId")
    suspend fun deleteLikeForBook(bookId: String, userId: String)

}