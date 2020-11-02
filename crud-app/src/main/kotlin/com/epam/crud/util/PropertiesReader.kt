package com.epam.crud.util

import java.util.*

abstract class PropertiesReader(propertiesPath: String) {
    val loader = Thread.currentThread().contextClassLoader
    val properties = Properties()
    val resourceStream = loader.getResourceAsStream(propertiesPath)

    init {
        properties.load(resourceStream)
    }
}