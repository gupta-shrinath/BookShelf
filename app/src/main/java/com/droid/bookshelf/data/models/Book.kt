package com.droid.bookshelf.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Book(
    val id: String,
    val image: String,
    val score: Double,
    val popularity: Int,
    val title: String,
     val publishedChapterDate: Long
)
