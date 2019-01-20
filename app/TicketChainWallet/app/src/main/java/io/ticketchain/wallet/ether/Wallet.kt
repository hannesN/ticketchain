package io.ticketchain.wallet.ether

import android.util.Log
import io.ticketchain.wallet.store.Ticket
import jota.IotaAPI
import jota.utils.TrytesConverter
import java.util.concurrent.CompletableFuture

class Wallet(val id:String) {

    private val TAG = "WALLET"

    private val ticketList = mutableListOf<Ticket>()


    fun getValidAdress() : String {
        return "gfdfjkdsfhksdahfjkadp"
    }

    fun getAllTickets(): List<Ticket> {
        if (ticketList.isEmpty()) {
            val event = "Konzert von X"
            val promoter = "SuperEvent Ltd."
            val date = "19-11-2019 20:00"
            val location = "Leipzig, Arena"



            for (i in 1..90) {
                ticketList.add(Ticket(getValidAdress(), i, event, location, promoter, date))
            }
        }
        return ticketList
    }
}