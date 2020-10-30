package com.epam.crud

import com.epam.crud.tables.Authors
import com.epam.crud.tables.Bookmarks
import com.epam.crud.tables.Books
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseManager {

    fun connect() = Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver", user = "", password = "")

    fun initData() {
        transaction {
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(Authors, Bookmarks, Books)

            Authors.insert {
                it[name] = "Pushkin"
                it[secondName] = "Alexander"
                it[lastName] = "Sergeevic"
            }

            Books.insert {
                it[bookName] = "Skazka o ribake i ribke"
                it[releaseYear] = 1835
                it[isbn] = "0000-0000-0000"
                it[publisher] = "AST"
                it[pageCount] = 12
                it[authorId] = 1
            }

            Bookmarks.insert {
                it[bookId] = 1
                it[pageNumber] = 2
            }
        }

    }


}