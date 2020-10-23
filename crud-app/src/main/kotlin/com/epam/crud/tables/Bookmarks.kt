package com.epam.crud.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object Bookmarks : LongIdTable() {
    val bookId = long("book_id")
    val pageNumber = integer("page_number")

    override val primaryKey = PrimaryKey(id, name = "PK_Bookmark_ID")
}