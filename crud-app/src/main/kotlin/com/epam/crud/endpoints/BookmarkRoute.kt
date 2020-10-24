package com.epam.crud.endpoints

import com.epam.crud.services.BookmarkService
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.bookmarkRout(bookmarkService: BookmarkService) {

    route("/bookmark") {
        post {
            var parameters = call.receiveParameters()

            bookmarkService.addBookmark(
                    parameters["bookId"]?.toLong(),
                    parameters["pageNumber"]?.toInt()
            )

            call.respond("Success!")
        }
        get("/getAll") {
            call.respond(bookmarkService.getAllBookmarks())
        }
        get("/{id}") {
            call.respond(bookmarkService.getById(call.parameters["id"]!!.toLong()))
        }
        delete("/{id}") {
            bookmarkService.deleteById(call.parameters["id"]!!.toLong())
            call.respond("Success!")
        }
    }

}

