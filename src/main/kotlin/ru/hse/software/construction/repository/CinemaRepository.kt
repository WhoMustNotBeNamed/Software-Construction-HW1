package ru.hse.software.construction.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import ru.hse.software.construction.model.Session
import java.io.File

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

class CinemaRepository(private val objectMapper: ObjectMapper = ObjectMapper().registerKotlinModule().registerModule(JavaTimeModule())) {

    fun saveSession(session: Session, filePath: String) {
        val jsonString = objectMapper.writeValueAsString(session)
        File(filePath).writeText(jsonString)
    }

    fun loadSession(filePath: String): Session {
        val jsonString = File(filePath).readText()
        return objectMapper.readValue(jsonString)
    }
}