package com.epam.crud.app

import com.epam.crud.DBManager
import com.epam.crud.endpoints.authorRout
import com.epam.crud.endpoints.bookRout
import com.epam.crud.endpoints.bookmarkRout
import com.epam.crud.endpoints.swaggerRout
import com.epam.crud.services.AuthorService
import com.epam.crud.services.BookService
import com.epam.crud.services.BookmarkService
import com.papsign.ktor.openapigen.OpenAPIGen
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*


fun main(args: Array<String>) {
    connectDb()
    startServer()
}

fun connectDb() {
    val manager = DBManager()
    manager.connect()
}


fun startServer() {
    embeddedServer(Netty, 8080) {
        install(OpenAPIGen) {
            serveSwaggerUi = true
            swaggerUiPath = "/swagger-ui"
        }
        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
        }
        val authorService = AuthorService()
        val bookService = BookService()
        val bookmarkService = BookmarkService()

        install(Routing) {
            authorRout(authorService)
            bookRout(bookService)
            bookmarkRout(bookmarkService)
            swaggerRout()
        }
    }.start(wait = true)
}

