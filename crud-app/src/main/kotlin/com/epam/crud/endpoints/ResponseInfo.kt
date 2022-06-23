package com.epam.crud.endpoints

import io.ktor.http.*

data class ResponseInfo(val code: HttpStatusCode, val message: String)