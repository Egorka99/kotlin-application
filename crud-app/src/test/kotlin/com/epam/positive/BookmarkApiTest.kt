package com.epam

import com.epam.app.testModule
import com.epam.crud.data.DatabaseManager
import com.epam.crud.dto.BookmarkDto
import com.epam.crud.services.BookmarkService
import com.epam.crud.tables.Bookmarks
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

class BookmarkApiTest() {

    private val service = BookmarkService()
    private val gson = GsonBuilder().create();

    private val data = BookmarkDto(1,2)

    @Before
    fun clear() {
        withTestApplication(Application::testModule) {
            val dbManager = DatabaseManager("src/test/resources/application.properties")
            dbManager.clearData()
            dbManager.initData()
        }
    }

    @Test
    fun getAllBookmarksTest() = withTestApplication(Application::testModule) {
        with(handleRequest(HttpMethod.Get, "bookmark/getAll")) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(service.getAllBookmarks().size, 2)
        }
    }

    @Test
    fun getBookmarkByIdTest() = withTestApplication(Application::testModule) {
        with(handleRequest(HttpMethod.Get, "bookmark/1")) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(service.getById(1), data)
        }
    }

    @Test
    fun addBookmarkTest() = withTestApplication(Application::testModule) {
        with(handleRequest(HttpMethod.Post, "bookmark") {
            addHeader("accept", "application/json")
            addHeader("Content-Type", "application/json")
            setBody(gson.toJson(data))
        }) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(service.getById(2), data)
        }
    }

    @Test
    fun deleteBookmarkByIdTest() = withTestApplication(Application::testModule) {
        with(handleRequest(HttpMethod.Delete, "bookmark/1")) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
        transaction {
            assertEquals(Bookmarks.select(Bookmarks.id.eq(1L)).toList().size, 0)
        }
    }


}

