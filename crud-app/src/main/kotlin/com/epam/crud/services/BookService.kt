package com.epam.crud.services

import com.epam.crud.dto.BookDto
import com.epam.crud.exceptions.BookOperationException
import com.epam.crud.tables.Books
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class BookService {

    fun addBook(book: BookDto) = transaction {
        addLogger(StdOutSqlLogger)
        try {
            Books.insert {
                it[bookName] = book.bookName
                it[releaseYear] = book.releaseYear
                it[isbn] = book.isbn
                it[publisher] = book.publisher
                it[authorId] = book.authorId
                it[pageCount] = book.pageCount
            }
        } catch (ex: Exception) {
            throw BookOperationException("Failed to add book")
        }

    }

    fun getAllBooks(): List<BookDto> = transaction {
        addLogger(StdOutSqlLogger)
        val books = Books.selectAll().map { rowToBookDto(it) }
        if (books.isEmpty()) {
            throw BookOperationException("Books not found")
        }
        books
    }

    fun getById(id: Long): BookDto = transaction {
        addLogger(StdOutSqlLogger)
        val book = Books.select { Books.id eq id }.map { a -> rowToBookDto(a) }
        if (book.isEmpty()) {
            throw BookOperationException("Book with such Id was not found")
        }
        book[0]
    }

    fun deleteById(id: Long) = transaction {
        addLogger(StdOutSqlLogger)
        val book = Books.select { Books.id eq id }.map { a -> rowToBookDto(a) }
        if (book.isEmpty()) {
            throw BookOperationException("Book with such Id was not found")
        }
        Books.deleteWhere { Books.id eq id }

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

}