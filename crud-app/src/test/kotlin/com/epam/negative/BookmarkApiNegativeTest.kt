package com.epam.negative

import com.epam.app.testModule
import com.epam.crud.data.DatabaseManager
import com.epam.crud.endpoints.ResponseInfo
import com.epam.crud.tables.Bookmarks
import com.google.gson.GsonBuilder
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class BookmarkApiNegativeTest {
    private val dbManager = DatabaseManager("src/test/resources/application.properties")
    private val gson = GsonBuilder().create();

    private val incorrectData = "{\n" +
            "    \"pageNumber\": 2\n" +
            "}"

    private val errorResponseGetAll = ResponseInfo(HttpStatusCode.InternalServerError, "Bookmarks not found")
    private val errorResponseGet = ResponseInfo(HttpStatusCode.InternalServerError, "Bookmark with such Id was not found")
    private val errorResponseDelete = ResponseInfo(HttpStatusCode.InternalServerError, "Bookmark with such Id was not found")
    private val errorResponsePost = ResponseInfo(HttpStatusCode.InternalServerError, "Failed to add bookmark")

    @Before
    fun clear() {
        withTestApplication(Application::testModule) {
            dbManager.clearData()
            dbManager.initData()
        }
    }

    @Test
    fun getAllBookmarksTest() = withTestApplication(Application::testModule) {
        transaction {
            Bookmarks.deleteAll()
        }
        with(handleRequest(HttpMethod.Get, "bookmark/getAll")) {
            Assert.assertEquals(HttpStatusCode.OK, response.status())
            Assert.assertEquals(errorResponseGetAll, gson.fromJson(response.content, ResponseInfo::class.java))
        }
    }

    @Test
    fun getBookmarkByIdTest() = withTestApplication(Application::testModule) {
        with(handleRequest(HttpMethod.Get, "bookmark/3")) {
            Assert.assertEquals(HttpStatusCode.OK, response.status())
            Assert.assertEquals(errorResponseGet, gson.fromJson(response.content, ResponseInfo::class.java))
        }
    }

    @Test
    fun deleteBookmarkByIdTest() = withTestApplication(Application::testModule) {
        with(handleRequest(HttpMethod.Delete, "bookmark/4")) {
            Assert.assertEquals(HttpStatusCode.OK, response.status())
            Assert.assertEquals(errorResponseDelete, gson.fromJson(response.content, ResponseInfo::class.java))
        }
    }

}