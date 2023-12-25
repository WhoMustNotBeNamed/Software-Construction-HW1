package ru.hse.software.construction.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.security.MessageDigest

class Account {
    @JsonProperty("users")
    val users: MutableList<User> = mutableListOf()

    fun register(username: String, password: String): Boolean {
        // Проверка наличия пользователя с таким же именем
        if (users.any { it.username == username }) {
            println("Пользователь с именем $username уже существует.")
            return false
        }

        // Хеширование пароля
        val passwordHash = hashPassword(password)

        // Добавление нового пользователя
        val newUser = User(username, passwordHash)
        users.add(newUser)
        println("Пользователь $username успешно зарегистрирован.")
        return true
    }

    fun login(username: String, password: String): Boolean {
        // Поиск пользователя с заданным именем
        val user = users.find { it.username == username }

        // Проверка наличия пользователя и совпадения хешированного пароля
        if (user != null && validatePassword(password, user.password)) {
            println("Вход пользователя $username выполнен успешно.")
            return true
        } else {
            println("Неверное имя пользователя или пароль.")
            return false
        }
    }

    private fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val passwordBytes = password.toByteArray()
        val hashBytes = md.digest(passwordBytes)
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    private fun validatePassword(inputPassword: String, storedPasswordHash: String): Boolean {
        val inputPasswordHash = hashPassword(inputPassword)
        return inputPasswordHash == storedPasswordHash
    }
}