package ru.hse.software.construction.controller

import ru.hse.software.construction.model.Movie
import ru.hse.software.construction.model.Session
import ru.hse.software.construction.repository.CinemaRepository
import ru.hse.software.construction.view.ConsoleView
import ru.hse.software.construction.view.SessionView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.system.exitProcess

class CinemaController() {

    // Scanner для ввода с консоли
    private val scanner = Scanner(System.`in`)

    // Форматтер для парсинга даты
    private val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    // Метод для отображения меню расписания сеансов
    fun scheduleMenu(session: Session) {
        val sessionView = SessionView(listOf(session))
        val consoleView = ConsoleView()
        val cinemaRepository = CinemaRepository()
        var choice: Int

        do {
            // Отображение расписания сеансов
            sessionView.displaySessionSchedule()

            // Отображение меню расписания
            consoleView.displayScheduleMenu()

            // Считывание выбора пользователя
            choice = scanner.nextInt()

            // Обработка выбора пользователя
            when (choice) {
                1 -> addMovie(session)
                2 -> removeMovie(session)
                3 -> {
                    println("Введите название фильма")
                    val title = readLine()
                    val movie = session.movies.find { it.title == title }
                    if (movie != null) {
                        if (title != null) {
                            movieMenu(session, title)
                        }
                    }
                }
            }
        } while (choice != 0)

        // Завершение программы
        cinemaRepository.saveSession(session, "session.json")
        exitProcess(0)
    }

    // Метод для добавления нового фильма
    private fun addMovie(session: Session) {
        println("Введите название фильма")
        val title = readln()
        println("Введите жанр фильма")
        val genre = readln()
        println("Введите имя режиссера фильма")
        val director = readln()
        println("Введите дату выхода фильма (в формате dd-MM-yyyy)")
        val releaseDateStr = readln()

        try {
            // Парсинг введенной даты почему-то ошибка
            //val releaseDate = LocalDateTime.parse(releaseDateStr, formatter)
            val movie = Movie(title, genre, director, LocalDateTime.now())
            session.addMovie(movie)
        } catch (e: Exception) {
            println("Ошибка при вводе даты. Пожалуйста, убедитесь, что вы используете правильный формат.")
        }
    }

    // Метод для удаления фильма
    private fun removeMovie(session: Session) {
        println("Введите название фильма")
        val title = readln()
        session.removeMovie(title)
    }

    // Метод для отображения меню фильма
    fun movieMenu(session: Session, movieTitle: String) {
        var title = movieTitle
        val consoleView = ConsoleView()
        val sessionView = SessionView(listOf(session))
        var choice: Int

        do {
            // Отображение информации о фильме и залах
            sessionView.showMovie(session, title)
            sessionView.displayCinemaHall(session, title)
            consoleView.displayMovieMenu()
            choice = scanner.nextInt()

            // Обработка выбора пользователя
            when (choice) {
                1 -> title = updateMovie(session, title)
                2 -> buyTicket(session, title)
                3 -> returnTicket(session, title)
                4 -> occupiedSeat(session, title)
            }
        } while (choice != 0)

        // Возвращение в меню расписания
        scheduleMenu(session)
    }

    // Метод для обновления информации о фильме
    private fun updateMovie(session: Session, movieTitle: String) : String {
        var title = movieTitle
        println("Введите название фильма")
        val newTitle = readln()
        println("Введите жанр фильма")
        val genre = readln()
        println("Введите имя режиссера фильма")
        val director = readln()
        println("Введите дату выхода фильма (в формате dd-MM-yyyy)")
        val releaseDateStr = readln()

        try {
            // Парсинг введенной даты почему-то ошибка
            //val releaseDate = LocalDateTime.parse(releaseDateStr, formatter)
            val newMovie = Movie(newTitle, genre, director, LocalDateTime.now())
            session.updateMovie(title, newMovie)
            title = newMovie.title
        } catch (e: Exception) {
            println("Ошибка при вводе даты. Пожалуйста, убедитесь, что вы используете правильный формат.")
        }
        return title
    }

    // Метод для покупки билета
    private fun buyTicket(session: Session, movieTitle: String) {
        println("Введите номер места")
        val seatNumber = scanner.nextInt()
        session.buyTicket(movieTitle, seatNumber)
    }

    // Метод для возврата билета
    private fun returnTicket(session: Session, movieTitle: String) {
        println("Введите номер места")
        val seatNumber = scanner.nextInt()
        session.returnTicket(movieTitle, seatNumber)
    }

    // Метод для отметки места как занятого
    private fun occupiedSeat(session: Session, movieTitle: String) {
        println("Введите номер места")
        val seatNumber = scanner.nextInt()
        session.occupiedSeat(movieTitle, seatNumber)
    }
}
