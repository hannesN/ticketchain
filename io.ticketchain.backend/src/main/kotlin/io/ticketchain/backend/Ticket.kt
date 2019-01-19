package io.ticketchain.backend

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.text.SimpleDateFormat
import java.util.*

val ISO_8601 = "yyyy-MM-dd'T'HH:mm'Z'"

data class Ticket(
    val event: String,
    val location: String,
    val promoter: String,
    val date: Date
) {
    constructor(ticketWithToken: TicketWithToken) : this(
        ticketWithToken.event,
        ticketWithToken.location,
        ticketWithToken.promoter,
        ticketWithToken.date
    )
}

data class TicketCreation(val ticket: Ticket, val count: Int)

data class TicketWithToken(
    val event: String,
    val location: String,
    val promoter: String,
    val date: Date,
    val token: String
) {
    constructor(ticket: Ticket, token: String) : this(
        ticket.event,
        ticket.location,
        ticket.promoter,
        ticket.date,
        token
    )
}

class TicketConverter {
    companion object {
        fun toJson(ticket: Ticket): String {
            return gson().toJson(ticket)
        }

        fun toJson(ticket: List<TicketWithToken>): String {
            return gson().toJson(ticket)
        }

        fun fromJson(json: String): TicketCreation {
            return gson().fromJson(json, TicketCreation::class.java)
        }

        fun withToken(ticket: Ticket, token: String): TicketWithToken {
            return TicketWithToken(ticket.event, ticket.location, ticket.promoter, ticket.date, token)
        }

        fun withoutToken(ticket: TicketWithToken): Ticket {
            return Ticket(ticket.event, ticket.location, ticket.promoter, ticket.date)
        }

        private fun gson(): Gson {
            val builder = GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat(ISO_8601) // ISO-8601
                .create()
            return builder
        }
    }
}

fun main(args: Array<String>) {
    println(SimpleDateFormat(ISO_8601).format(Date()))
}
