package ru.hse.software.construction.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class Session(
    val sessionId: Int,
    var ticketPrice: Int,
    @JsonFormat(pattern = "dd-MM-yyyy")
    var startDateTime: LocalDateTime,
    val duration: Long,
    val seatsInHall: Int = 100,
    val movies: MutableList<Movie> = mutableListOf(),
) {

    fun addMovie(movie : Movie) {
        movies.add(movie)
    }

    fun updateMovie(movieTitle: String, newMovieInfo: Movie) {
        val existingMovie = movies.find { it.title == movieTitle }
        existingMovie?.let {
            it.title = newMovieInfo.title
            it.genre = newMovieInfo.genre
            it.director = newMovieInfo.director
            it.releaseDate = newMovieInfo.releaseDate
        }
    }

    fun removeMovie(movieTitle: String) {
        movies.removeIf { it.title == movieTitle }
    }

    fun changeStartDateTime(newStartDateTime: LocalDateTime) {
        startDateTime = newStartDateTime
    }

    fun calculateEndDateTime(): LocalDateTime {
        return startDateTime.plusMinutes(duration)
    }

    fun buyTicket(movieTitle: String, seatNumber: Int) {
        movies.find{it.title == movieTitle}?.purchasedTickets?.add(Ticket(seatNumber))
    }

    fun returnTicket(movieTitle: String, seatNumber: Int) {
        movies.find{it.title == movieTitle}?.purchasedTickets?.removeIf { it.seatNumber == seatNumber }
    }

    fun occupiedSeat(movieTitle: String, seatNumber: Int) {
        movies.find{it.title == movieTitle}?.purchasedTickets?.removeIf { it.seatNumber == seatNumber }
        movies.find{it.title == movieTitle}?.occupiedSeats?.add(Ticket(seatNumber))
    }
}