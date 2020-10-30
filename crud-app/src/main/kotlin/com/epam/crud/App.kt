package com.epam.crud

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
import org.apache.log4j.Logger


fun main(args: Array<String>) {
    Application.run()
}

object Application {

    private val logger = Logger.getLogger(javaClass.name);

    fun run() {
        connectDb()
        initDb()
        startServer()

    }

    private fun connectDb() {
        logger.info("Connect to database..")
        DatabaseManager.connect()
    }

    private fun initDb() {
        logger.info("Init test data..")
        DatabaseManager.initData()
    }

    private fun startServer() {
        logger.info("Start application server..")
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
            logger.info("Application launched!")
        }.start(wait = true)
    }
}




