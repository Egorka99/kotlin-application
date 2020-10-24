package com.epam.crud.services

import com.epam.crud.DBManager
import com.epam.crud.dto.AuthorDto
import com.epam.crud.entities.Author
import com.epam.crud.tables.Authors
import com.epam.crud.tables.Authors.secondName
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class AuthorService {

    val manager = DBManager()

    fun addAuthor(name: String?, secondName: String?, lastName: String?) = transaction {
        manager.initData()
        addLogger(StdOutSqlLogger)
        Author.new {
            this.name = name ?: "none"
            this.secondName = secondName ?: "none"
            this.lastName = lastName ?: "none"
        }
    }

    fun getAllAuthors(): List<AuthorDto> = transaction {
        manager.initData()
        addLogger(StdOutSqlLogger)
        Authors.selectAll().map { rowToAuthorDto(it) }
    }

    fun getById(id: Long): AuthorDto = transaction {
        manager.initData()
        addLogger(StdOutSqlLogger)
        authorToAuthorDto(Author[id])
    }

    fun deleteById(id: Long) = transaction {
        manager.initData()
        addLogger(StdOutSqlLogger)
        val author = Author[id]
        author.delete()
    }

    private fun rowToAuthorDto(row: ResultRow): AuthorDto {
        return AuthorDto(
                secondName = row[secondName],
                name = row[Authors.name],
                lastName = row[Authors.lastName]
        )
    }

    private fun authorToAuthorDto(author: Author): AuthorDto {
        return AuthorDto(
                secondName = author.secondName,
                name = author.name,
                lastName = author.lastName
        )
    }
}