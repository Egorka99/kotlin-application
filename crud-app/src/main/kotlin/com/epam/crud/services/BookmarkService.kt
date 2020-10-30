package com.epam.crud.services

import com.epam.crud.dto.BookmarkDto
import com.epam.crud.exceptions.BookmarkOperationException
import com.epam.crud.tables.Bookmarks
import com.epam.crud.tables.Bookmarks.bookId
import com.epam.crud.tables.Bookmarks.pageNumber
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class BookmarkService {

    fun addBookmark(bookmark: BookmarkDto) = transaction {
        addLogger(StdOutSqlLogger)
        Bookmarks.insert {
            it[bookId] = bookmark.bookId
            it[pageNumber] = bookmark.pageNumber
        }
    }

    fun getAllBookmarks(): List<BookmarkDto> = transaction {
        addLogger(StdOutSqlLogger)
        Bookmarks.selectAll().map { rowToBookmarkDto(it) }
    }

    fun getById(id: Long): BookmarkDto = transaction {
        addLogger(StdOutSqlLogger)
        val bookmark = Bookmarks.select { Bookmarks.id eq id }.map { a -> rowToBookmarkDto(a) }
        if (bookmark.isEmpty()) {
            throw BookmarkOperationException()
        }
        bookmark[0]
    }

    fun deleteById(id: Long) = transaction {
        addLogger(StdOutSqlLogger)
        Bookmarks.deleteWhere { Bookmarks.id eq id }
    }

    private fun rowToBookmarkDto(row: ResultRow): BookmarkDto {
        return BookmarkDto(
                bookId = row[bookId],
                pageNumber = row[pageNumber]
        )
    }

}