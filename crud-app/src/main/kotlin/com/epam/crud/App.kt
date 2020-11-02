package com.epam.crud

import com.epam.crud.data.DatabaseManager
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
import org.apache.log4j.Logger

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val logger = Logger.getLogger(javaClass.name);

    val dbManager = DatabaseManager("application.properties")

    logger.info("Connect to database..")
    dbManager.connect()
    logger.info("Init test data..")
    dbManager.initData()

    logger.info("Start application server..")

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
}







