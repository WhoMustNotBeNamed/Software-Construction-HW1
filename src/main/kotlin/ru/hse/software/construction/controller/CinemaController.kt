package ru.hse.software.construction.controller

import ru.hse.software.construction.model.Movie
import ru.hse.software.construction.model.Session
import ru.hse.software.construction.repository.CinemaRepository
import ru.hse.software.construction.view.ConsoleView
import ru.hse.software.construction.view.SessionView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*
import kotlin.system.exitProcess

class CinemaController() {

    private val scanner = Scanner(System.`in`)
    private val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    /**
     * Метод отображения меню расписания сеансов
     */
    fun scheduleMenu(session: Session) {
        val sessionView = SessionView(listOf(session))
        val consoleView = ConsoleView()
        val cinemaRepository = CinemaRepository()
        var choice: Int = 4

        do {
            sessionView.displaySessionSchedule()
            consoleView.displayScheduleMenu()

            try {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt()
                } else {
                    throw InputMismatchException("Ошибка ввода. Введите корректное число.")
                }

                when (choice) {
                    1 -> addMovie(session)
                    2 -> removeMovie(session)
                    3 -> {
                        println("Введите название фильма")
                        val title = readLine()
                        if (title != null) {
                            val movie = session.movies.find { it.title == title }
                            if (movie != null) {
                                movieMenu(session, title)
                            }
                        }
                    }
                }
            } catch (e: InputMismatchException) {
                consoleView.printError("Ошибка ввода. Введите корректное число.")
                scanner.next() // Очистка буфера сканера
            }

        } while (choice != 0)

        cinemaRepository.saveSession(session, "session.json")
        exitProcess(0)
    }

    /**
     * Метод для добавления нового фильма
     */
    private fun addMovie(session: Session) {
        val consoleView = ConsoleView()
        println("Введите название фильма")
        val title: String? = readLine()
        if (title.isNullOrBlank()) {
            consoleView.printError("Ошибка ввода. Название фильма не может быть пустым.")
            return
        }

        println("Введите жанр фильма")
        val genre: String? = readLine()
        if (genre.isNullOrBlank()) {
            consoleView.printError("Ошибка ввода. Жанр фильма не может быть пустым.")
            return
        }

        println("Введите имя режиссера фильма")
        val director: String? = readLine()
        if (director.isNullOrBlank()) {
            consoleView.printError("Ошибка ввода. Имя режиссера не может быть пустым.")
            return
        }

        println("Введите дату выхода фильма (в формате dd-MM-yyyy)")
        val releaseDateStr: String? = readLine()
        if (releaseDateStr.isNullOrBlank()) {
            consoleView.printError("Ошибка ввода. Поле с датой не может быть пустым.")
            return
        }

        try {
            // Какая-то проблема с переводом строки в дату
            //val releaseDate = LocalDateTime.parse(releaseDateStr, formatter)
            val movie = Movie(title, genre, director, LocalDateTime.now())
            session.addMovie(movie)
        } catch (e: DateTimeParseException) {
            consoleView.printError("Ошибка при вводе даты. Пожалуйста, убедитесь, что вы используете правильный формат.")
        }
    }

    /**
     * Метод для удаления фильма
     */
    private fun removeMovie(session: Session) {
        val consoleView = ConsoleView()
        println("Введите название фильма")
        val title: String? = readLine()
        if (title.isNullOrBlank()) {
            consoleView.printError("Ошибка ввода. Название фильма не может быть пустым.")
            return
        }

        session.removeMovie(title)
    }

    /**
     * Метод для отображения меню фильма
     */
    fun movieMenu(session: Session, movieTitle: String) {
        var title = movieTitle
        val consoleView = ConsoleView()
        val sessionView = SessionView(listOf(session))
        var choice: Int = 5

        do {
            sessionView.showMovie(session, title)
            sessionView.displayCinemaHall(session, title)
            consoleView.displayMovieMenu()

            try {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt()
                } else {
                    throw InputMismatchException("Ошибка ввода. Введите корректное число.")
                }

                when (choice) {
                    1 -> title = updateMovie(session, title)
                    2 -> buyTicket(session, title)
                    3 -> returnTicket(session, title)
                    4 -> occupiedSeat(session, title)
                }
            } catch (e: InputMismatchException) {
                consoleView.printError("Ошибка ввода. Введите корректное число.")
                scanner.next() // Очистка буфера сканера
            }

        } while (choice != 0)

        scheduleMenu(session)
    }

    /**
     * Метод для обновления информации о фильме
     */
    private fun updateMovie(session: Session, movieTitle: String): String {
        var title = movieTitle
        val consoleView = ConsoleView()
        println("Введите название фильма")
        val newTitle: String? = readLine()
        if (newTitle.isNullOrBlank()) {
            consoleView.printError("Ошибка ввода. Название фильма не может быть пустым.")
            return title
        }

        println("Введите жанр фильма")
        val genre: String? = readLine()
        if (genre.isNullOrBlank()) {
            consoleView.printError("Ошибка ввода. Жанр фильма не может быть пустым.")
            return title
        }

        println("Введите имя режиссера фильма")
        val director: String? = readLine()
        if (director.isNullOrBlank()) {
            consoleView.printError("Ошибка ввода. Имя режиссера не может быть пустым.")
            return title
        }

        println("Введите дату выхода фильма (в формате dd-MM-yyyy)")
        val releaseDateStr: String? = readLine()
        if (releaseDateStr.isNullOrBlank()) {
            consoleView.printError("Ошибка ввода. Поле с датой не может быть пустым.")
            return title
        }

        try {
            // Какая-то проблема с переводом строки в дату
            //val releaseDate = LocalDateTime.parse(releaseDateStr, formatter)
            val newMovie = Movie(newTitle, genre, director, LocalDateTime.now())
            session.updateMovie(title, newMovie)
            title = newMovie.title
        } catch (e: DateTimeParseException) {
            consoleView.printError("Ошибка при вводе даты. Пожалуйста, убедитесь, что вы используете правильный формат.")
        }
        return title
    }

    /**
     * Метод для покупки билета
     */
    private fun buyTicket(session: Session, movieTitle: String) {
        val consoleView = ConsoleView()
        println("Введите номер места")
        try {
            val seatNumber: Int = scanner.nextInt()
            session.buyTicket(movieTitle, seatNumber)
        } catch (e: InputMismatchException) {
            consoleView.printError("Ошибка ввода. Введите корректный номер места.")
            scanner.next() // Очистка буфера сканера
        }
    }

    /**
     * Метод для возврата билета
     */
    private fun returnTicket(session: Session, movieTitle: String) {
        val consoleView = ConsoleView()
        println("Введите номер места")
        try {
            val seatNumber: Int = scanner.nextInt()
            session.returnTicket(movieTitle, seatNumber)
        } catch (e: InputMismatchException) {
            consoleView.printError("Ошибка ввода. Введите корректный номер места.")
            scanner.next() // Очистка буфера сканера
        }
    }

    /**
     * Метод для занятия места
     */
    private fun occupiedSeat(session: Session, movieTitle: String) {
        val consoleView = ConsoleView()
        println("Введите номер места")
        try {
            val seatNumber: Int = scanner.nextInt()
            session.occupiedSeat(movieTitle, seatNumber)
        } catch (e: InputMismatchException) {
            consoleView.printError("Ошибка ввода. Введите корректный номер места.")
            scanner.next() // Очистка буфера сканера
        }
    }
}
