package com.epam.crud.endpoints

import com.epam.crud.services.BookService
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.bookRout(bookService: BookService) {

    route("/book") {
        post {
            var parameters = call.receiveParameters()

            bookService.addBook(
                    parameters["bookName"],
                    parameters["releaseYear"]?.toInt(),
                    parameters["isbn"],
                    parameters["publisher"],
                    parameters["authorId"]?.toLong(),
                    parameters["pageCount"]?.toInt()
            )

            call.respond("Success!")
        }
        get("/getAll") {
            call.respond(bookService.getAllBooks())
        }
        get("/{id}") {
            call.respond(bookService.getById(call.parameters["id"]!!.toLong()))
        }
        delete("/{id}") {
            bookService.deleteById(call.parameters["id"]!!.toLong())
            call.respond("Success!")
        }
    }

}

