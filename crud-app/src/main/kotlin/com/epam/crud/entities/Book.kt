package com.epam.crud.entities

import com.epam.crud.tables.Books
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Book(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Book>(Books)

    var bookName by Books.bookName
    var releaseYear by Books.releaseYear
    var isbn by Books.isbn
    var publisher by Books.publisher
    var authorId by Books.authorId
    var pageCount by Books.pageCount
}