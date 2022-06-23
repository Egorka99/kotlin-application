package com.epam.chat.client

import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@KtorExperimentalAPI
suspend fun main() {
    ChatClient().main()
}

class ChatClient {

    val host = "localhost"
    val port = 8080
    val wsPath = "/ws"

    @KtorExperimentalAPI
    val client = HttpClient {
        install(WebSockets)
    }

    @KtorExperimentalAPI
    private suspend fun createSession() {
        client.ws(
                method = HttpMethod.Get,
                host = host,
                port = port, path = wsPath
        ) {

            println("Welcome to chat!")
            println("Enter a message:")

            launch {
                while (true) {
                    val line = withContext(Dispatchers.IO) { readLine() } ?: ""
                    send(line)
                }
            }

            while (true) {
                when (val frame = incoming.receive()) {
                    is Frame.Text -> println(frame.readText())
                }
            }

        }
    }

    @KtorExperimentalAPI
    suspend fun main() {
        createSession()
    }
}