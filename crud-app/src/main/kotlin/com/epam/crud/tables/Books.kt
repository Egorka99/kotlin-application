package com.epam.crud.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object Books : LongIdTable() {
    val bookName = varchar("name", 50)
    val releaseYear = integer("release_year")
    val isbn = varchar("isbn", 25)
    val publisher = varchar("publisher", 50)
    val authorId = long("author_id");
    val pageCount = integer("page_count")

    override val primaryKey = PrimaryKey(id, name = "PK_Book_ID")
}