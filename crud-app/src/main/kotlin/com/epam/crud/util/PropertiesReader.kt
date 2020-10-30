package com.epam.crud.util

import java.io.FileInputStream
import java.util.*

abstract class PropertiesReader {
    protected val properties = Properties()
    private val fis = FileInputStream("crud-app/src/main/resources/application.properties")

    init {
        properties.load(fis)
    }
}