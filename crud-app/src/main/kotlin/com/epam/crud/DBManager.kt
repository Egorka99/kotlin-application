package com.epam.crud

import com.epam.crud.entities.Author
import com.epam.crud.entities.Book
import com.epam.crud.entities.Bookmark
import com.epam.crud.tables.Authors
import com.epam.crud.tables.Bookmarks
import com.epam.crud.tables.Books
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class DBManager {
    fun connect() = Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "", password = "")

    fun initData() {
        transaction {
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(Authors, Bookmarks, Books)

            Author.new {
                name = "Pushkin"
                secondName = "Alexander"
                lastName = "Sergeevic"
                dob = LocalDate.of(1799, 1, 1)
            }

            Book.new {
                bookName = "Skazka o ribake i ribke"
                releaseYear = 1835
                isbn = "0000-0000-0000"
                publisher = "AST"
                pageCount = 12
                authorId = 1
            }

            Bookmark.new {
                bookId = 1
                pageNumber = 2
            }

        }
    }


}