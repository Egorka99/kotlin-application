package com.epam.crud.dto

import java.time.LocalDate

data class AuthorDto(
        val name: String,
        val secondName: String,
        val lastName: String,
        val dob: LocalDate
)