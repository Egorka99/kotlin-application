package com.epam.crud.services

import com.epam.crud.dto.BookDto
import com.epam.crud.entities.Book
import com.epam.crud.tables.Books
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class BookService {


    fun addBook(book: BookDto) = transaction {
        addLogger(StdOutSqlLogger)
        Books.insert {
            it[bookName] = book.bookName
            it[releaseYear] = book.releaseYear
            it[isbn] = book.isbn
            it[publisher] = book.publisher
            it[authorId] = book.authorId
            it[pageCount] = book.pageCount
        }
    }

    fun getAllBooks(): List<BookDto> = transaction {
        addLogger(StdOutSqlLogger)
        Books.selectAll().map { rowToBookDto(it) }
    }

    fun getById(id: Long): BookDto = transaction {
        addLogger(StdOutSqlLogger)
        bookToBookDto(Book[id])
    }

    fun deleteById(id: Long) = transaction {
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