package com.epam.crud.endpoints

import com.epam.crud.dto.BookmarkDto
import com.epam.crud.exceptions.BookmarkOperationException
import com.epam.crud.services.BookmarkService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.bookmarkRout(bookmarkService: BookmarkService) {

    route("/bookmark") {
        post {
            try {
                val dto = call.receive<BookmarkDto>()
                bookmarkService.addBookmark(dto)
            } catch (ex: UnsupportedMediaTypeException) {
                call.respond(ResponseInfo(HttpStatusCode.UnsupportedMediaType, "Incorrect request media type"))
            }

            call.respond(ResponseInfo(HttpStatusCode.OK, "Success!"))
        }
        get("/getAll") {
            try {
                call.respond(bookmarkService.getAllBookmarks())
            } catch (ex: BookmarkOperationException) {
                call.respond(ResponseInfo(HttpStatusCode.InternalServerError, ex.message.toString()))
            }

        }
        get("/{id}") {
            try {
                call.respond(bookmarkService.getById(call.parameters["id"]!!.toLong()))
            } catch (ex: BookmarkOperationException) {
                call.respond(ResponseInfo(HttpStatusCode.InternalServerError, ex.message.toString()))
            }

        }
        delete("/{id}") {
            bookmarkService.deleteById(call.parameters["id"]!!.toLong())

            call.respond(ResponseInfo(HttpStatusCode.OK, "Success!"))
        }
    }

}
