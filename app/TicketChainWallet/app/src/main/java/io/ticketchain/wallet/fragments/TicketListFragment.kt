package io.ticketchain.wallet.fragments

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.ticketchain.wallet.R
import io.ticketchain.wallet.ether.Wallet
import io.ticketchain.wallet.store.Ticket

class TicketListFragment() : AbstractFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.list_tickets, container, false)

        val wallet: Wallet = getIotaFactory().createWallet()
        container!!.context.getSharedPreferences("TicketChain", Context.MODE_PRIVATE)

        view.findViewById<RecyclerView>(R.id.ticketListView).apply {
            setHasFixedSize(false)
            adapter = Adapter(wallet)
            layoutManager = LinearLayoutManager(container.context)
        }
        return view
    }

    private class Adapter(val wallet: Wallet) : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_ticket_item, parent, false))
        }

        override fun getItemCount(): Int {
            return wallet.getAllTickets().size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val ticket = wallet.getAllTickets().get(position)
            holder.setValues(ticket)
        }
    }

    private class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun setValues(ticket: Ticket) {
            view.findViewById<TextView>(R.id.eventNameText).text = ticket.event
            view.findViewById<TextView>(R.id.promoterText).text = ticket.promoter
            view.findViewById<TextView>(R.id.dateText).text = ticket.date
            view.findViewById<TextView>(R.id.locationView).text = ticket.location
        }
    }

}
