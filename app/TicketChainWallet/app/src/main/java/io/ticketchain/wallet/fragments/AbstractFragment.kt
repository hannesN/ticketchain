package io.ticketchain.wallet.fragments

import android.content.Context
import android.support.v4.app.Fragment
import io.ticketchain.wallet.MainActivity
import io.ticketchain.wallet.ether.EtherFactory
import io.ticketchain.wallet.ether.Wallet

abstract class AbstractFragment : Fragment() {
    private var mainActivtiy: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mainActivtiy = context as MainActivity
    }

    override fun onDetach() {
        this.mainActivtiy = null
        super.onDetach()
    }

    protected fun getIotaFactory():EtherFactory {
       return mainActivtiy!!.etherFactory
    }

    protected fun getWallet() : Wallet {
        return mainActivtiy!!.wallet1
    }
}