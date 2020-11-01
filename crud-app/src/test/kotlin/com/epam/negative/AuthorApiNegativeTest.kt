package com.epam.negative

import com.epam.app.testModule
import com.epam.crud.data.DatabaseManager
import com.epam.crud.endpoints.ResponseInfo
import com.epam.crud.tables.Authors
import com.google.gson.GsonBuilder
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AuthorApiNegativeTest {
    private val dbManager = DatabaseManager("src/test/resources/application.properties")
    private val gson = GsonBuilder().create();

    private val incorrectData = "{\"name\":\"Pushkin\",\"secondName\":\"Alexander\"}"

    private val errorResponseGetAll = ResponseInfo(HttpStatusCode.InternalServerError, "Authors not found")
    private val errorResponseGet = ResponseInfo(HttpStatusCode.InternalServerError, "Author with such Id was not found")
    private val errorResponseDelete = ResponseInfo(HttpStatusCode.InternalServerError, "Author with such Id was not found")
    private val errorResponsePost = ResponseInfo(HttpStatusCode.InternalServerError, "Failed to add author")

    @Before
    fun clear() {
        withTestApplication(Application::testModule) {
            dbManager.clearData()
            dbManager.initData()
        }
    }

    @Test
    fun getAllAuthorsTest() = withTestApplication(Application::testModule) {
        transaction {
            Authors.deleteAll()
        }
        with(handleRequest(HttpMethod.Get, "author/getAll")) {
            Assert.assertEquals(HttpStatusCode.OK, response.status())
            Assert.assertEquals(errorResponseGetAll, gson.fromJson(response.content, ResponseInfo::class.java))
        }
    }

    @Test
    fun getAuthorByIdTest() = withTestApplication(Application::testModule) {
        with(handleRequest(HttpMethod.Get, "author/3")) {
            Assert.assertEquals(HttpStatusCode.OK, response.status())
            Assert.assertEquals(errorResponseGet, gson.fromJson(response.content, ResponseInfo::class.java))
        }
    }

    @Test
    fun deleteAuthorByIdTest() = withTestApplication(Application::testModule) {
        with(handleRequest(HttpMethod.Delete, "author/4")) {
            Assert.assertEquals(HttpStatusCode.OK, response.status())
            Assert.assertEquals(errorResponseDelete, gson.fromJson(response.content, ResponseInfo::class.java))
        }
    }

    @Test
    fun addAuthorTest() = withTestApplication(Application::testModule) {
        with(handleRequest(HttpMethod.Post, "author") {
            addHeader("accept", "application/json")
            addHeader("Content-Type", "application/json")
            setBody(incorrectData)
        }) {
            Assert.assertEquals(HttpStatusCode.OK, response.status())
            Assert.assertEquals(errorResponsePost, gson.fromJson(response.content, ResponseInfo::class.java))
        }
    }


}