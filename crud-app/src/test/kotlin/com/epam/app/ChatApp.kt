package com.epam.app

import io.ktor.application.*
import io.ktor.http.cio.websocket.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.websocket.*
import org.apache.log4j.Logger
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.ArrayList


class Client(val session: DefaultWebSocketSession) {
    companion object {
        var lastId = AtomicInteger(1)
    }

    val id = lastId.getAndIncrement()
    val name = "user$id"
}


object ChatService {

    val logger = Logger.getLogger(ChatService::class.simpleName)

    val clients = Collections.synchronizedSet(HashSet<Client>())

    val lastMessages = ArrayList<String>()

    suspend fun broadcast(message: String) {
        for (client in clients) {
            client.session.outgoing.send(Frame.Text(message))
        }
    }

    suspend fun sendLastMessages(client: Client) {
        for (message in lastMessages) {
            client.session.outgoing.send(Frame.Text(message))
        }
    }

    fun clear() {
        Client.lastId = AtomicInteger(1)
        clients.clear()
        lastMessages.clear()
    }

}

fun Application.chatTestModule() {
    install(WebSockets)
    install(Sessions)

    ChatService.logger.info("Server started!")

    routing {
        webSocket("/ws") {
            val client = Client(this)
            ChatService.clients += client
            ChatService.logger.info("${client.name} connected")
            ChatService.sendLastMessages(client)
            try {
                while (true) {
                    when (val frame = incoming.receive()) {
                        is Frame.Text -> {
                            val text = "[${client.name}] ${frame.readText()}"
                            ChatService.broadcast(text)
                            ChatService.lastMessages += text
                        }
                    }
                }
            } finally {
                ChatService.clients -= client
                ChatService.logger.info("${client.name} disconnected")
            }
        }
    }
}