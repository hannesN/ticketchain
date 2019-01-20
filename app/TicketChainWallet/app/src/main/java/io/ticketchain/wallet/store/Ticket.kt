package io.ticketchain.wallet.store

import java.util.*

data class Ticket(
    val owner: String,
    val ticketId: Int,
    val event: String,
    val location: String,
    val promoter: String,
    val date: String
) {

}