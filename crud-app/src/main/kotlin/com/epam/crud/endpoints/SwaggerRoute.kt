package com.epam.crud.endpoints

import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.swaggerRout() {

    static("/static") {
        resources("files")
    }

    route("/api") {
        get {
            call.respondRedirect("/swagger-ui/index.html?url=/static/swagger.json", true)
        }
    }

}
