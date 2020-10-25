package com.epam.crud.endpoints

import com.epam.crud.services.AuthorService
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.routing.post

fun Route.authorRout(authorService: AuthorService) {

    route("/author") {
        static("/static") {
            resources("files")
        }
        post {
            var parameters = call.receiveParameters()

            authorService.addAuthor(
                    parameters["name"],
                    parameters["secondName"],
                    parameters["lastName"]
            )

            call.respond("Success!")
        }
        get("/getAll") {
            call.respond(authorService.getAllAuthors())
        }
        get("/{id}") {
            call.respond(authorService.getById(call.parameters["id"]!!.toLong()))
        }
        delete("/{id}") {
            authorService.deleteById(call.parameters["id"]!!.toLong())
            call.respond("Success!")
        }
    }

}

