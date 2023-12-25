package ru.hse.software.construction.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import ru.hse.software.construction.model.Session
import ru.hse.software.construction.view.ConsoleView
import java.io.File
import java.io.IOException
import java.time.LocalDateTime

class CinemaRepository(private val objectMapper: ObjectMapper = ObjectMapper().registerKotlinModule().registerModule(JavaTimeModule())) {

    fun saveSession(session: Session, filePath: String) {
        try {
            val jsonString = objectMapper.writeValueAsString(session)
            File(filePath).writeText(jsonString)
        } catch (e: IOException) {
            val consoleView = ConsoleView()
            consoleView.printError("Ошибка при записи файла: $e")
        }
    }

    fun loadSession(filePath: String): Session {
        try {
            val jsonString = File(filePath).readText()
            return objectMapper.readValue(jsonString)
        } catch (e: IOException) {
            val consoleView = ConsoleView()
            consoleView.printError("Ошибка при чтении файла: $e")
            return Session(1, 100, LocalDateTime.now(), 90) // Заглушка, чтобы вернуть пустой объект Session в случае ошибки
        }
    }
}