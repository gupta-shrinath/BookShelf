package com.droid.bookshelf.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "books")
@JsonClass(generateAdapter = true)
data class Book(
    @PrimaryKey
    val id: String,
    val image: String,
    val score: Double,
    val title: String,
    val publishedChapterDate: Long? = null
)
