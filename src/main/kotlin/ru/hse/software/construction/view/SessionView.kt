package ru.hse.software.construction.view

import ru.hse.software.construction.model.Session
import java.time.format.DateTimeFormatter

class SessionView(
    private val sessionsInStock: List<Session>
) {
    private val dateFormatterFullTime: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    // ANSI Escape коды для цветов
    private val ANSI_RESET = "\u001B[0m"
    private val ANSI_BLUE = "\u001B[34m"
    private val ANSI_YELLOW = "\u001B[33m"
    private val ANSI_ORANGE = "\u001B[38;5;208m"

    // Функция для создания афиши в кинотеатре
    fun displaySessionSchedule() {
        println("${ANSI_ORANGE}Афиша:${ANSI_RESET}")

        sessionsInStock.forEach { session ->
            var movieNumber = 1

            session.movies.forEach { movie ->
                println("${ANSI_BLUE}$movieNumber. Фильм: ${movie.title}${ANSI_RESET}")
                println("   Сеанс: ${session.sessionId}")
                val formattedStartDateTime = session.startDateTime.format(dateFormatterFullTime)
                println("   Дата и время начала: $formattedStartDateTime")
                movieNumber++
            }
        }
    }

    // Метод для вывода информации о занятых местах
    fun displayCinemaHall(session: Session, title: String) {
        val sqrtSeatsInHall = kotlin.math.sqrt(session.seatsInHall.toDouble()).toInt()

        println("Информация о занятых местах:")
        print("     ")

        for (i in 1..sqrtSeatsInHall) {
            print("${ANSI_BLUE}  $i")
        }

        println("\n    --------------------------------")

        for (row in 1..sqrtSeatsInHall) {
            val rowLabel = if (row >= 10) "${ANSI_BLUE}$row  | ${ANSI_RESET}" else "${ANSI_BLUE}$row   | ${ANSI_RESET}"
            print(rowLabel)

            for (seatNumber in 1..sqrtSeatsInHall) {
                val seatIndex = seatNumber + (row - 1) * sqrtSeatsInHall
                val marker = when {
                    session.movies.find { it.title == title }?.purchasedTickets?.any {
                        it.seatNumber == seatIndex
                    } == true -> "${ANSI_YELLOW} # ${ANSI_RESET}" // Желтый цвет для проданных билетов
                    session.movies.find { it.title == title }?.occupiedSeats?.any {
                        it.seatNumber == seatIndex
                    } == true -> "${ANSI_ORANGE} X ${ANSI_RESET}" // Оранжевый цвет для занятых мест
                    else -> " 0 "
                }
                print(marker)
            }

            println()
        }

        println()
    }

    // Метод для отображения информации о конкретном фильме
    fun showMovie(session: Session, movieTitle: String) {
        val movie = session.movies.find { it.title == movieTitle }

        movie?.let {
            println("Информация о фильме:")
            println("Название: ${movie.title}")
            println("Жанр: ${movie.genre}")
            println("Режиссер: ${movie.director}")
            println("Дата выхода: ${movie.releaseDate.format(dateFormatter)}")
        } ?: println("Фильм не найден.")
    }
}
