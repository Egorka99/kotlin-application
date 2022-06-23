package com.epam.negative

import com.epam.app.testModule
import com.epam.crud.data.DatabaseManager
import com.epam.crud.endpoints.ResponseInfo
import com.epam.crud.tables.Books
import com.google.gson.GsonBuilder
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class BookApiNegativeTest {
    private val dbManager = DatabaseManager("src/test/resources/application.properties")
    private val gson = GsonBuilder().create();

    private val incorrectData = "{\n" +
            "    \"bookName\": \"Skazka o ribake i ribke\",\n" +
            "    \"releaseYear\": 1835,\n" +
            "    \"publisher\": \"AST\",\n" +
            "    \"authorId\": 1,\n" +
            "    \"pageCount\": 12\n" +
            "}"

    private val errorResponseGetAll = ResponseInfo(HttpStatusCode.InternalServerError, "Books not found")
    private val errorResponseGet = ResponseInfo(HttpStatusCode.InternalServerError, "Book with such Id was not found")
    private val errorResponseDelete = ResponseInfo(HttpStatusCode.InternalServerError, "Book with such Id was not found")
    private val errorResponsePost = ResponseInfo(HttpStatusCode.InternalServerError, "Failed to add book")

    @Before
    fun clear() {
        withTestApplication(Application::testModule) {
            dbManager.clearData()
            dbManager.initData()
        }
    }

    @Test
    fun getAllBooksTest() = withTestApplication(Application::testModule) {
        transaction {
            Books.deleteAll()
        }
        with(handleRequest(HttpMethod.Get, "book/getAll")) {
            Assert.assertEquals(HttpStatusCode.OK, response.status())
            Assert.assertEquals(errorResponseGetAll, gson.fromJson(response.content, ResponseInfo::class.java))
        }
    }

    @Test
    fun getBookByIdTest() = withTestApplication(Application::testModule) {
        with(handleRequest(HttpMethod.Get, "book/3")) {
            Assert.assertEquals(HttpStatusCode.OK, response.status())
            Assert.assertEquals(errorResponseGet, gson.fromJson(response.content, ResponseInfo::class.java))
        }
    }

    @Test
    fun deleteBookByIdTest() = withTestApplication(Application::testModule) {
        with(handleRequest(HttpMethod.Delete, "book/4")) {
            Assert.assertEquals(HttpStatusCode.OK, response.status())
            Assert.assertEquals(errorResponseDelete, gson.fromJson(response.content, ResponseInfo::class.java))
        }
    }

    @Test
    fun addBookTest() = withTestApplication(Application::testModule) {
        with(handleRequest(HttpMethod.Post, "book") {
            addHeader("accept", "application/json")
            addHeader("Content-Type", "application/json")
            setBody(incorrectData)
        }) {
            Assert.assertEquals(HttpStatusCode.OK, response.status())
            Assert.assertEquals(errorResponsePost, gson.fromJson(response.content, ResponseInfo::class.java))
        }
    }


}