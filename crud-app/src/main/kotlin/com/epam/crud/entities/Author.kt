package com.epam.crud.entities

import com.epam.crud.tables.Authors
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Author(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Author>(Authors)

    var name by Authors.name
    var secondName by Authors.secondName
    var lastName by Authors.lastName
}