package com.epam.crud.services

import com.epam.crud.DBManager
import com.epam.crud.dto.AuthorDto
import com.epam.crud.entities.Author
import com.epam.crud.tables.Authors
import com.epam.crud.tables.Authors.secondName
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class AuthorService {
    fun getAllAuthors(): List<AuthorDto> = transaction {
        val manager = DBManager()

        manager.initData()

        Authors.selectAll().map { toUsers(it) }
    }

    private fun toUsers(row: ResultRow): AuthorDto {
        return AuthorDto(
                secondName = row[secondName],
                name = row[Authors.name],
                lastName = row[Authors.lastName],
                dob = row[Authors.dob]
        )
    }
}