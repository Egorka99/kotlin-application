package com.epam.crud.services

import com.epam.crud.dto.BookmarkDto
import com.epam.crud.entities.Bookmark
import com.epam.crud.tables.Bookmarks
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class BookmarkService {

    fun addBookmark(bookId: Long?, pageNumber: Int?) = transaction {
        addLogger(StdOutSqlLogger)
        Bookmark.new {
            this.bookId = bookId ?: 0
            this.pageNumber = pageNumber ?: 0
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
                bookId = row[Bookmarks.bookId],
                pageNumber = row[Bookmarks.pageNumber]
        )
    }

    private fun bookmarkToBookmarkDto(Bookmark: Bookmark): BookmarkDto {
        return BookmarkDto(
                bookId = Bookmark.bookId,
                pageNumber = Bookmark.pageNumber
        )
    }
}