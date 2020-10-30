package com.epam.crud.util

class DatabasePropertiesReader : PropertiesReader() {

    val url: String = properties.getProperty("db.url")
    val driver: String = properties.getProperty("db.driver")
    val user: String = properties.getProperty("db.user")
    val password: String = properties.getProperty("db.password")

}