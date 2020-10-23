package com.epam.crud.app

import com.epam.crud.tables.Authors
import com.epam.crud.tables.Bookmarks
import com.epam.crud.tables.Books
import com.epam.crud.entities.Author
import com.epam.crud.entities.Book
import com.epam.crud.entities.Bookmark
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

fun main() {
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "", password = "")

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(Authors,Bookmarks,Books)

        val pushkin = Author.new {
            name = "Pushkin"
            secondName = "Alexander"
            lastName = "Sergeevic"
            dob = LocalDate.of(1799,1,1)
        }

        val fairyTale = Book.new {
           bookName = "Skazka o ribake i ribke"
           releaseYear = 1835
           isbn = "0000-0000-0000"
           publisher = "AST"
           pageCount = 12
           authorId = 1
        }

        val bookmark = Bookmark.new {
            bookId = 1
            pageNumber = 2
        }

    }

    Thread.sleep(10000)

}
