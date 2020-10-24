package com.epam.crud.services

import com.epam.crud.DBManager
import com.epam.crud.dto.BookDto
import com.epam.crud.entities.Book
import com.epam.crud.tables.Books
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class BookService {

    val manager = DBManager()

    fun addBook(bookName: String?, releaseYear: Int?, isbn: String?, publisher: String?, authorId: Long?, pageCount: Int?) = transaction {
        manager.initData()
        addLogger(StdOutSqlLogger)
        Book.new {
            this.bookName = bookName ?: "none"
            this.releaseYear = releaseYear ?: 0
            this.isbn = isbn ?: "none"
            this.publisher = publisher ?: "none"
            this.authorId = authorId ?: 0
            this.pageCount = pageCount ?: 0
        }
    }

    fun getAllBooks(): List<BookDto> = transaction {
        manager.initData()
        addLogger(StdOutSqlLogger)
        Books.selectAll().map { rowToBookDto(it) }
    }

    fun getById(id: Long): BookDto = transaction {
        manager.initData()
        addLogger(StdOutSqlLogger)
        bookToBookDto(Book[id])
    }

    fun deleteById(id: Long) = transaction {
        manager.initData()
        addLogger(StdOutSqlLogger)
        val book = Book[id]
        book.delete()
    }

    private fun rowToBookDto(row: ResultRow): BookDto {
        return BookDto(
                bookName = row[Books.bookName],
                releaseYear = row[Books.releaseYear],
                isbn = row[Books.isbn],
                publisher = row[Books.publisher],
                authorId = row[Books.authorId],
                pageCount = row[Books.pageCount]
        )
    }

    private fun bookToBookDto(book: Book): BookDto {
        return BookDto(
                bookName = book.bookName,
                releaseYear = book.releaseYear,
                isbn = book.isbn,
                publisher = book.publisher,
                authorId = book.authorId,
                pageCount = book.pageCount
        )
    }


}