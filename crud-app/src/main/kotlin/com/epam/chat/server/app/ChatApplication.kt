package com.epam.chat.server.app

import com.epam.chat.server.ChatServer
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.cio.websocket.*
import io.ktor.http.cio.websocket.CloseReason
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.sessions.*
import io.ktor.util.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import java.time.Duration


data class ChatSession(val id: String)

fun main() {

    val server = ChatServer()

    embeddedServer(Netty, 8080) {

        install(DefaultHeaders)

        install(WebSockets) {
            pingPeriod = Duration.ofMinutes(1)
        }

        install(Sessions) {
            cookie<ChatSession>("SESSION")
        }

        intercept(ApplicationCallPipeline.Features) {
            if (call.sessions.get<ChatSession>() == null) {
                call.sessions.set(ChatSession(generateNonce()))
            }
        }

        println("Server started!")

        routing {

            webSocket("/ws") {

                val session = call.sessions.get<ChatSession>()

                if (session == null) {
                    close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session"))
                    return@webSocket
                }

                server.memberJoin(session.id, this)

                try {
                    incoming.consumeEach { frame ->
                        if (frame is Frame.Text) {
                            server.message(session.id, frame.readText())
                        }
                    }
                } finally {
                    server.memberLeft(session.id, this)
                }
            }

        }
    }.start(wait = true)
}


