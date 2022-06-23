package com.epam.positive

import com.epam.app.testModule
import com.epam.crud.data.DatabaseManager
import com.epam.crud.dto.BookDto
import com.epam.crud.services.BookService
import com.epam.crud.tables.Books
import com.google.gson.GsonBuilder
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BookApiTest() {

    private val service = BookService()
    private val gson = GsonBuilder().create();

    private val data = BookDto("Skazka o ribake i ribke", 1835, "0000-0000-0000", "AST", 1, 12)

    @Before
    fun clear() {
        withTestApplication(Application::testModule) {
            val dbManager = DatabaseManager("src/test/resources/application.properties")
            dbManager.clearData()
            dbManager.initData()
        }
    }

    @Test
    fun getAllBooksTest() = withTestApplication(Application::testModule) {
        with(handleRequest(HttpMethod.Get, "book/getAll")) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(service.getAllBooks().size, 2)
        }
    }

    @Test
    fun getBookByIdTest() = withTestApplication(Application::testModule) {
        with(handleRequest(HttpMethod.Get, "book/1")) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(service.getById(1), data)
        }
    }

    @Test
    fun addBookTest() = withTestApplication(Application::testModule) {
        with(handleRequest(HttpMethod.Post, "book") {
            addHeader("accept", "application/json")
            addHeader("Content-Type", "application/json")
            setBody(gson.toJson(data))
        }) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(service.getById(2), data)
        }
    }

    @Test
    fun deleteBookByIdTest() = withTestApplication(Application::testModule) {
        with(handleRequest(HttpMethod.Delete, "book/1")) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
        transaction {
            assertEquals(Books.select(Books.id.eq(1L)).toList().size, 0)
        }
    }


}

