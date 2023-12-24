package ru.hse.software.construction.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class Movie(
    var title: String,
    var genre: String,
    var director: String,
    @JsonFormat(pattern = "dd-MM-yyyy")
    var releaseDate: LocalDateTime,
    val occupiedSeats: MutableList<Ticket> = mutableListOf(),
    val purchasedTickets: MutableList<Ticket> = mutableListOf()
) {}