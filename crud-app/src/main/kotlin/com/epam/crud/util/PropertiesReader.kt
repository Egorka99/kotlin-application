package com.epam.crud.util

import java.io.FileInputStream
import java.util.*

abstract class PropertiesReader(propertiesPath: String) {
    protected val properties = Properties()
    private val fis = FileInputStream(propertiesPath)

    init {
        properties.load(fis)
    }
}