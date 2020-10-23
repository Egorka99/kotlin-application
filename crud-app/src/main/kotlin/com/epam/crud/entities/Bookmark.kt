package com.epam.crud.entities

import com.epam.crud.tables.Bookmarks
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Bookmark(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Bookmark>(Bookmarks)

    var bookId by Bookmarks.bookId
    var pageNumber by Bookmarks.pageNumber
}