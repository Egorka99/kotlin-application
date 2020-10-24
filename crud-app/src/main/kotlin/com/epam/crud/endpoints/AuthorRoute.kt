package com.epam.crud.endpoints

import com.epam.crud.services.AuthorService
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.authorRout(authorService: AuthorService) {

    route("/author") {
        get("/getAll") {
            call.respond(authorService.getAllAuthors())
        }
    }
}