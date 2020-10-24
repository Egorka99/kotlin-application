package com.epam.crud.dto

data class BookDto(
        val bookName: String,
        val releaseYear: Int,
        val isbn: String,
        val publisher: String,
        val authorId: Long,
        val pageCount: Int
)
