import com.epam.crud.entities.Author
import com.epam.crud.entities.Book
import com.epam.crud.tables.Authors
import com.epam.crud.tables.Books
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.BeforeClass
import org.junit.Test

class DatabaseManagerIntegrationTest {

    companion object {

        val url = "jdbc:h2:./src/test/resources/test"
        val driver = "org.h2.Driver"
        val user = ""
        val password = ""

        @BeforeClass
        fun initData() {
            Database.connect(url, driver = driver, user = user, password = password)
            transaction {

                SchemaUtils.drop(Authors)
                SchemaUtils.drop(Books)
                SchemaUtils.create(Authors)

                Author.new {
                    name = "Pushkin"
                    secondName = "Alexander"
                    lastName = "Sergeevic"
                }
            }

        }
    }

    @Test
    fun `Finding database driver test`() {
        Class.forName(driver)
    }

    @Test
    fun `Database connection test`() {
        Database.connect(url, driver = driver, user = user, password = password)
    }

    @Test
    fun `Reading from database test`() {
        transaction {
            Author[1]
        }
    }

    @Test
    fun `Writing to database test`() {
        transaction {
            SchemaUtils.create(Books)

            Book.new {
                bookName = "Skazka o ribake i ribke"
                releaseYear = 1835
                isbn = "0000-0000-0000"
                publisher = "AST"
                pageCount = 12
                authorId = 1
            }

            Author[1]
        }
    }

}