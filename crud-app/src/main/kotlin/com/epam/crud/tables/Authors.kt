package com.epam.crud.tables

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.`java-time`.date

object Authors : LongIdTable() {
    val name = varchar("name", 50)
    val secondName = varchar("second_name", 50)
    val lastName = varchar("last_name", 50)
    val dob = date("date_of_birth")

    override val primaryKey = PrimaryKey(id, name = "PK_Author_ID")

}