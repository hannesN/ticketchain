package io.ticketchain.wallet.iota

import jota.IotaAPI
import java.lang.RuntimeException
import java.util.concurrent.CompletableFuture

class IOTAFactory {
    private var iotaClient: IotaAPI? = null


    init {
        iotaClient = CompletableFuture.supplyAsync({
            IotaAPI.Builder()
                .protocol("https")
                .host("altnodes.devnet.iota.org")
                .port("443")
                .build()
        }).get()

    }

    fun createWallet(): Wallet {
        val wallet = iotaClient?.let { Wallet(it) }

        if (wallet === null) {
            throw RuntimeException("Invalid State: No IOTA connection found")
        }

        return wallet
    }

    fun createTransaction(sender: Wallet, receiver: Wallet): Transaction {
        val transaction = iotaClient?.let { Transaction(it, sender, receiver) }
        if (transaction === null) {
            throw RuntimeException("Invalid State: No IOTA connection found")
        }

        return transaction
    }
}