package com.epam.crud.endpoints

import com.epam.crud.dto.BookmarkDto
import com.epam.crud.services.BookmarkService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException

fun Route.bookmarkRout(bookmarkService: BookmarkService) {

    route("/bookmark") {
        post {
            try {
                val dto = call.receive<BookmarkDto>()
                bookmarkService.addBookmark(dto)
            } catch (ex: UnsupportedMediaTypeException) {
                ex.printStackTrace()
                call.respond(ResponseInfo(415, "Incorrect request media type"))
            } catch (ex: Exception) {
                call.respond(ResponseInfo(500, ex.message.toString()))
            }

            call.respond(ResponseInfo(200, "Success!"))
        }
        get("/getAll") {
            try {
                call.respond(bookmarkService.getAllBookmarks())
            } catch (ex: EntityNotFoundException) {
                call.respond(ResponseInfo(500, "Bookmark with such Id was not found"))
            } catch (ex: Exception) {
                call.respond(ResponseInfo(500, ex.message.toString()))
            }

        }
        get("/{id}") {
            try {
                call.respond(bookmarkService.getById(call.parameters["id"]!!.toLong()))
            } catch (ex: EntityNotFoundException) {
                call.respond(ResponseInfo(500, "Bookmark with such Id was not found"))
            } catch (ex: Exception) {
                call.respond(ResponseInfo(500, ex.message.toString()))
            }

        }
        delete("/{id}") {
            bookmarkService.deleteById(call.parameters["id"]!!.toLong())

            call.respond(ResponseInfo(200, "Success!"))
        }
    }

}
