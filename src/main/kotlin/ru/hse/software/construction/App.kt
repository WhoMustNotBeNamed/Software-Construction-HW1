package ru.hse.software.construction

import ru.hse.software.construction.controller.CinemaController
import ru.hse.software.construction.model.Movie
import ru.hse.software.construction.model.Session
import ru.hse.software.construction.view.SessionView
import java.time.LocalDateTime

fun main() {
    val movie1 = Movie("Фильм 1", "Жанр 1", "Режиссер 1", LocalDateTime.now())
    val movie2 = Movie("Фильм 2", "Жанр 2", "Режиссер 2", LocalDateTime.now())

    val session1 = Session(1, 100, LocalDateTime.now(), 120)
    session1.addMovie(movie1)

    val session2 = Session(2, 120, LocalDateTime.now().plusHours(2), 90)
    session1.addMovie(movie2)

    val sessionView = SessionView(listOf(session1, session2))
    //sessionView.displaySessionSchedule()

    // Update the movie information
    val updatedMovieInfo = Movie(
        title = "Inception 2.0",
        genre = "Sci-Fi/Action",
        director = "Christopher Nolan",
        releaseDate =  LocalDateTime.now()
    )
    session1.updateMovie("Фильм 2", updatedMovieInfo)

    //sessionView.displaySessionSchedule()
    session1.buyTicket("Фильм 1", 3)
    session1.buyTicket("Фильм 1", 32)
    session1.occupiedSeat("Фильм 1", 4)
    session1.occupiedSeat("Фильм 1", 3)


    session1.occupiedSeat("Inception 2.0", 1)
    session1.occupiedSeat("Inception 2.0", 2)
    session1.occupiedSeat("Inception 2.0", 3)
    session1.occupiedSeat("Inception 2.0", 4)
    session1.occupiedSeat("Inception 2.0", 5)
    session1.occupiedSeat("Inception 2.0", 6)
    session1.occupiedSeat("Inception 2.0", 7)
    session1.occupiedSeat("Inception 2.0", 8)
    session1.occupiedSeat("Inception 2.0", 9)
    session1.occupiedSeat("Inception 2.0", 10)
    session1.occupiedSeat("Inception 2.0", 11)
    session1.occupiedSeat("Inception 2.0", 12)
    session1.occupiedSeat("Inception 2.0", 13)
    session1.occupiedSeat("Inception 2.0", 14)



    //sessionView.displayCinemaHall(session1, "Фильм 1")
    //sessionView.displayCinemaHall(session1, "Inception 2.03")

    //sessionView.showMovie(session1, "Фильм 1")

    val cinemaController = CinemaController()
    cinemaController.scheduleMenu(session1)
}