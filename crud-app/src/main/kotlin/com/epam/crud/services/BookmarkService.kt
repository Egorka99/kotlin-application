package com.epam.crud.services

import com.epam.crud.dto.BookmarkDto
import com.epam.crud.entities.Bookmark
import com.epam.crud.tables.Bookmarks
import com.epam.crud.tables.Bookmarks.bookId
import com.epam.crud.tables.Bookmarks.pageNumber
import com.epam.crud.tables.Books
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
        bookmarkToBookmarkDto(Bookmark[id])
    }

    fun deleteById(id: Long) = transaction {
        addLogger(StdOutSqlLogger)
        val bookmark = Bookmark[id]
        bookmark.delete()
    }

    private fun rowToBookmarkDto(row: ResultRow): BookmarkDto {
        return BookmarkDto(
                bookId = row[bookId],
                pageNumber = row[pageNumber]
        )
    }

    private fun bookmarkToBookmarkDto(Bookmark: Bookmark): BookmarkDto {
        return BookmarkDto(
                bookId = Bookmark.bookId,
                pageNumber = Bookmark.pageNumber
        )
    }
}