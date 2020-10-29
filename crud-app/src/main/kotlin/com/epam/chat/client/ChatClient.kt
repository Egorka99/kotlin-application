package com.epam.chat.client

import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


suspend fun main() {
    ChatClient().main()
}

class ChatClient {

    val exitWord = "exit"
    val host = "localhost"
    val port = 8080
    val wsPath = "/ws"

    val client = HttpClient {
        install(WebSockets)
    }

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
                    val line = readLine() ?: ""
                    if (line == exitWord) break else send(line)
                }
            }

            while (true) {
                when (val frame = incoming.receive()) {
                    is Frame.Text -> println(frame.readText())
                }
            }

        }
    }

    suspend fun main() {
        createSession()
    }
}