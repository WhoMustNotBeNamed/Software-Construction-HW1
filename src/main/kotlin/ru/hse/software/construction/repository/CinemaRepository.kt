package ru.hse.software.construction.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File

class CinemaRepository(private val filePath: String) {
    /*
    private val objectMapper = ObjectMapper().registerKotlinModule()

    fun saveData() {
        File(filePath).writeText(objectMapper.writeValueAsString())
    }

    fun loadData() {
        val json = File(filePath).readText()
        return objectMapper.readValue(json)
    }

     */
}
