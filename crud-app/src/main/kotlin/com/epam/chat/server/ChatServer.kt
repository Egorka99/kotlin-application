package com.epam.chat.server

import io.ktor.application.*
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.sessions.*
import io.ktor.websocket.*
import org.apache.log4j.Logger
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.ArrayList


fun main(args: Array<String>) {
    ChatServer.run()
}

class Client(val session: DefaultWebSocketSession) {
    companion object {
        var lastId = AtomicInteger(1)
    }

    val id = lastId.getAndIncrement()
    val name = "user$id"
}


class ChatServer {

    companion object {
        private val logger = Logger.getLogger(ChatServer::class.simpleName)

        private val clients = Collections.synchronizedSet(HashSet<Client>())

        private val lastMessages = ArrayList<String>()

        fun run() {
            startServer()
        }

        private fun startServer() {
            embeddedServer(Netty, 8080) {
                install(WebSockets)
                install(Sessions)

                logger.info("Server started!")

                routing {
                    webSocket("/ws") {
                        val client = Client(this)
                        clients += client
                        logger.info("${client.name} connected")
                        sendLastMessages(client)
                        try {
                            while (true) {
                                when (val frame = incoming.receive()) {
                                    is Frame.Text -> {
                                        val text = "[${client.name}] ${frame.readText()}"
                                        broadcast(text)
                                        lastMessages += text
                                    }
                                }
                            }
                        } finally {
                            clients -= client
                            logger.info("${client.name} disconnected")
                        }
                    }
                }
            }.start(wait = true)
        }

        private suspend fun broadcast(message: String) {
            for (client in clients) {
                client.session.outgoing.send(Frame.Text(message))
            }
        }

        private suspend fun sendLastMessages(client: Client) {
            for (message in lastMessages) {
                client.session.outgoing.send(Frame.Text(message))
            }
        }
    }

}

