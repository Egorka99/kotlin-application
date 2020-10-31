package com.epam.crud.data

import com.epam.crud.tables.Authors
import com.epam.crud.tables.Bookmarks
import com.epam.crud.tables.Books
import com.epam.crud.util.DatabasePropertiesReader
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseManager(propertiesPath: String) {

    private val reader = DatabasePropertiesReader(propertiesPath)

    fun connect() = Database.connect(reader.url, reader.driver, reader.user, reader.password)

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

    fun clearData() {
        transaction {
            SchemaUtils.drop(Authors, Bookmarks, Books)
        }
    }


}