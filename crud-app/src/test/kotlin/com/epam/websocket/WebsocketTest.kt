package com.epam.websocket

import com.epam.app.ChatService
import com.epam.app.chatTestModule
import io.ktor.application.*
import io.ktor.http.cio.websocket.*
import io.ktor.server.testing.*
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class WebSocketTest {

    @Before
    fun chatServerClear() {
        ChatService.clear()
    }

    @Test
    fun callbackFromServerTest() = withTestApplication(Application::chatTestModule) {
        val response = "[user1] Hello world!"
        var message = ""
        handleWebSocketConversation("/ws") { incoming, outgoing ->

            outgoing.send(Frame.Text("Hello world!"))
            when (val frame = incoming.receive()) {
                is Frame.Text -> message = frame.readText()
            }
        }

        assertEquals(response, message)
    }

    @Test
    fun otherUserMessageCallbackTest() = withTestApplication(Application::chatTestModule) {
        val response = "[user1] Hello world!"
        var message = ""

        handleWebSocketConversation("/ws") { _, outgoing ->
            outgoing.send(Frame.Text("Hello world!"))

            handleWebSocketConversation("/ws") { incoming, _ ->
                when (val frame = incoming.receive()) {
                    is Frame.Text -> message = frame.readText()
                }
            }
        }

        assertEquals(response, message)
    }

    @Test
    fun severalMessagesCallbackTest() = withTestApplication(Application::chatTestModule) {
        val response = "[user1] Hello[user1] world!"
        var message = ""

        //Other user
        handleWebSocketConversation("/ws") { incoming, outgoing ->
            outgoing.send(Frame.Text("Hello"))
            outgoing.send(Frame.Text("world!"))

            for (i in 0 until 2) {
                when (val frame = incoming.receive()) {
                    is Frame.Text -> message += frame.readText()
                }
            }
        }
        assertEquals(response, message)
    }



}