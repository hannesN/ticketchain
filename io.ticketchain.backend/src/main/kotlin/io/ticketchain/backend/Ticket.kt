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
        ticketWithToken.ticket.event,
        ticketWithToken.ticket.location,
        ticketWithToken.ticket.promoter,
        ticketWithToken.ticket.date
    )
}

data class TicketCreation(val ticket: Ticket, val count: Int)

data class TicketWithToken(val ticket: Ticket,val token: String)

class TicketConverter {
    companion object {
        fun toJson(ticket: Ticket): String {
            return gson().toJson(ticket)
        }

        fun toJson(tickets: List<TicketWithToken>): String {
            return gson().toJson(tickets)
        }

        fun toJson(ticket: TicketWithToken): String {
            return gson().toJson(ticket)
        }

        fun fromJson(json: String): TicketCreation {
            return gson().fromJson(json, TicketCreation::class.java)
        }

        fun withToken(ticket: Ticket, token: String): TicketWithToken {
            return TicketWithToken(ticket, token)
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
