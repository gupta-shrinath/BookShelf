package com.droid.bookshelf.data.local.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "likes", indices = [Index(value = ["bookId", "userId"], unique = true)])
data class Like(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val bookId: String,
    val userId: String
)