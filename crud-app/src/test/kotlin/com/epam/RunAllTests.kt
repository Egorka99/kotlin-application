package com.epam

import com.epam.negative.AuthorApiNegativeTest
import com.epam.negative.BookApiNegativeTest
import com.epam.negative.BookmarkApiNegativeTest
import com.epam.positive.AuthorApiTest
import com.epam.positive.BookApiTest
import com.epam.positive.BookmarkApiTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(value = [
    AuthorApiNegativeTest::class,
    BookApiNegativeTest::class,
    BookmarkApiNegativeTest::class,
    AuthorApiTest::class,
    BookApiTest::class,
    BookmarkApiTest::class

])
class RunAllTests {
}