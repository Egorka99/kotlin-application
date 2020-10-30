package com.epam.crud.services

import com.epam.crud.dto.AuthorDto
import com.epam.crud.tables.Authors
import com.epam.crud.tables.Authors.secondName
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class AuthorService {

    fun addAuthor(author: AuthorDto) = transaction {
        addLogger(StdOutSqlLogger)
        Authors.insert {
            it[name] = author.name
            it[secondName] = author.secondName
            it[lastName] = author.lastName
        }
    }

    fun getAllAuthors(): List<AuthorDto> = transaction {
        addLogger(StdOutSqlLogger)
        Authors.selectAll().map { rowToAuthorDto(it) }
    }

    fun getById(id: Long): AuthorDto = transaction {
        addLogger(StdOutSqlLogger)
        Authors.select { Authors.id eq id }.map { a -> rowToAuthorDto(a) }[0]
    }

    fun deleteById(id: Long) = transaction {
        addLogger(StdOutSqlLogger)
        Authors.deleteWhere { Authors.id eq id }
    }

    private fun rowToAuthorDto(row: ResultRow): AuthorDto {
        return AuthorDto(
                secondName = row[secondName],
                name = row[Authors.name],
                lastName = row[Authors.lastName]
        )
    }

}