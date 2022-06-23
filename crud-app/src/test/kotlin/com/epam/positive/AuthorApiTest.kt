package com.epam.positive

import com.epam.app.testModule
import com.epam.crud.data.DatabaseManager
import com.epam.crud.dto.AuthorDto
import com.epam.crud.services.AuthorService
import com.epam.crud.tables.Authors
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

class AuthorApiTest() {
    private val service = AuthorService()
    private val gson = GsonBuilder().create();

    private val data = AuthorDto(name = "Pushkin", secondName = "Alexander", lastName = "Sergeevic")

    @Before
    fun clear() {
        withTestApplication(Application::testModule) {
            val dbManager = DatabaseManager("src/test/resources/application.properties")
            dbManager.clearData()
            dbManager.initData()
        }
    }

    @Test
    fun getAllAuthorsTest() = withTestApplication(Application::testModule) {
        with(handleRequest(HttpMethod.Get, "author/getAll")) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(service.getAllAuthors().size, 2)
        }
    }

    @Test
    fun getAuthorByIdTest() = withTestApplication(Application::testModule) {
        with(handleRequest(HttpMethod.Get, "author/1")) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(service.getById(1), data)
        }
    }

    @Test
    fun addAuthorTest() = withTestApplication(Application::testModule) {
        with(handleRequest(HttpMethod.Post, "author") {
            addHeader("accept", "application/json")
            addHeader("Content-Type", "application/json")
            setBody(gson.toJson(data))
        }) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(service.getById(2), data)
        }
    }

    @Test
    fun deleteAuthorByIdTest() = withTestApplication(Application::testModule) {
        with(handleRequest(HttpMethod.Delete, "author/1")) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
        transaction {
            assertEquals(Authors.select(Authors.id.eq(1L)).toList().size, 0)
        }
    }


}

