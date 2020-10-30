package com.epam.crud.endpoints

import com.epam.crud.dto.BookDto
import com.epam.crud.exceptions.BookOperationException
import com.epam.crud.services.BookService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.bookRout(bookService: BookService) {

    route("/book") {
        post {
            try {
                val dto = call.receive<BookDto>()
                bookService.addBook(dto)
            } catch (ex: UnsupportedMediaTypeException) {
                call.respond(ResponseInfo(HttpStatusCode.UnsupportedMediaType, "Incorrect request media type"))
            } catch (ex: Exception) {
                call.respond(ResponseInfo(HttpStatusCode.InternalServerError, ex.message.toString()))
            }

            call.respond(ResponseInfo(HttpStatusCode.OK, "Success!"))
        }
        get("/getAll") {
            try {
                call.respond(bookService.getAllBooks())
            } catch (ex: BookOperationException) {
                call.respond(ResponseInfo(HttpStatusCode.InternalServerError, "Books not found"))
            } catch (ex: Exception) {
                call.respond(ResponseInfo(HttpStatusCode.InternalServerError, ex.message.toString()))
            }

        }
        get("/{id}") {
            try {
                call.respond(bookService.getById(call.parameters["id"]!!.toLong()))
            } catch (ex: BookOperationException) {
                call.respond(ResponseInfo(HttpStatusCode.InternalServerError, "Book with such Id was not found"))
            } catch (ex: Exception) {
                call.respond(ResponseInfo(HttpStatusCode.InternalServerError, ex.message.toString()))
            }

        }
        delete("/{id}") {
            bookService.deleteById(call.parameters["id"]!!.toLong())

            call.respond(ResponseInfo(HttpStatusCode.OK, "Success!"))
        }
    }

}


