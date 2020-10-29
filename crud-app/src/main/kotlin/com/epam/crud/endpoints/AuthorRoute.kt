package com.epam.crud.endpoints

import com.epam.crud.dto.AuthorDto
import com.epam.crud.services.AuthorService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.exceptions.EntityNotFoundException

fun Route.authorRout(authorService: AuthorService) {

    route("/author") {
        post {
            try {
                val dto = call.receive<AuthorDto>()
                authorService.addAuthor(dto)
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
                call.respond(authorService.getAllAuthors())
            } catch (ex: EntityNotFoundException) {
                call.respond(ResponseInfo(500, "Author with such Id was not found"))
            } catch (ex: Exception) {
                call.respond(ResponseInfo(500, ex.message.toString()))
            }

        }
        get("/{id}") {
            try {
                call.respond(authorService.getById(call.parameters["id"]!!.toLong()))
            } catch (ex: EntityNotFoundException) {
                call.respond(ResponseInfo(500, "Author with such Id was not found"))
            } catch (ex: Exception) {
                call.respond(ResponseInfo(500, ex.message.toString()))
            }

        }
        delete("/{id}") {
            authorService.deleteById(call.parameters["id"]!!.toLong())

            call.respond(ResponseInfo(200, "Success!"))
        }
    }

}

