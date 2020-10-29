package com.epam.crud.app

import com.epam.crud.DatabaseManager
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
    Application.run()
}

object Application {

    fun run() {
        connectDb()
        initDb()
        startServer()
    }

    private fun connectDb() {
        DatabaseManager.connect()
    }

    private fun initDb() {
        DatabaseManager.initData()
    }

    private fun startServer() {
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

            install(Routing) {
                authorRout(AuthorService())
                bookRout(BookService())
                bookmarkRout(BookmarkService())
                swaggerRout()
            }
        }.start(wait = true)
    }
}




