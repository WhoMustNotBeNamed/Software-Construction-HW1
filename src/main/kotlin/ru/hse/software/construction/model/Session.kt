package ru.hse.software.construction.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class Session(
    val sessionId: Int,
    var ticketPrice: Int,
    var startDateTime: LocalDateTime,
    val duration: Long,
    val seatsInHall: Int = 100,
    val movies: MutableList<Movie> = mutableListOf(),
) {

    // Метод для добавления фильма
    fun addMovie(movie : Movie) {
        movies.add(movie)
    }

    // Метод для изменения инфомации о фильме
    fun updateMovie(movieTitle: String, newMovieInfo: Movie) {
        val existingMovie = movies.find { it.title == movieTitle }
        existingMovie?.let {
            it.title = newMovieInfo.title
            it.genre = newMovieInfo.genre
            it.director = newMovieInfo.director
            it.releaseDate = newMovieInfo.releaseDate
        }
    }

    // Метод для удаления фильма
    fun removeMovie(movieTitle: String) {
        movies.removeIf { it.title == movieTitle }
    }

    // Метод для изменения даты и времени начала сеанса
    fun changeStartDateTime(newStartDateTime: LocalDateTime) {
        startDateTime = newStartDateTime
    }

    // Метод для вычисления времени окончания сеанса
    fun calculateEndDateTime(): LocalDateTime {
        return startDateTime.plusMinutes(duration)
    }

    // Метод покупки билета
    fun buyTicket(movieTitle: String, seatNumber: Int) {
        movies.find{it.title == movieTitle}?.purchasedTickets?.add(Ticket(seatNumber))
    }

    // Метод возврата билета
    fun returnTicket(movieTitle: String, seatNumber: Int) {
        movies.find{it.title == movieTitle}?.purchasedTickets?.removeIf { it.seatNumber == seatNumber }
    }

    // Метод для занятия места
    fun occupiedSeat(movieTitle: String, seatNumber: Int) {
        movies.find{it.title == movieTitle}?.purchasedTickets?.removeIf { it.seatNumber == seatNumber }
        movies.find{it.title == movieTitle}?.occupiedSeats?.add(Ticket(seatNumber))
    }
}