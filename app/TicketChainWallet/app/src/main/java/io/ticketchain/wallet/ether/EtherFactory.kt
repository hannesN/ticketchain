package io.ticketchain.wallet.ether

import android.content.Context
import jota.IotaAPI
import java.lang.RuntimeException
import java.util.concurrent.CompletableFuture
import kotlin.random.Random

class EtherFactory(val context: Context) {

    fun createWallet(): Wallet {
        val id = "123456789"
        return Wallet(id)
    }


}