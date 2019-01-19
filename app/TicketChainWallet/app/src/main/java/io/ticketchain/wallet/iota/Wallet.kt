package io.ticketchain.wallet.iota

import android.util.Log
import jota.IotaAPI
import java.util.concurrent.CompletableFuture

class Wallet(val iotaApi: IotaAPI) {

    private val TAG = "WALLET"

    private val addressList = mutableListOf<String>()

    var id: String? = null
        set(value) {
            if (value?.length == 81) {
                field = value
            } else {
                throw RuntimeException("Invalid length of wallet id: " + value?.length)
            }
        }


    constructor (iotaApi: IotaAPI, id: String) : this(iotaApi) {
        this.id = id
    }

    fun getValidAdress() : String {
        val address = CompletableFuture.supplyAsync({
            val newAdress = iotaApi.generateNewAddresses(id, 2, false, 1)
            val addressString = newAdress.addresses.first()
            Log.d(TAG, "Address is: " + addressString)
            addressString
        }).get()

        if (!addressList.contains(address)) {
            addressList.add(address)
        }
        return address
    }

    fun getAllAddresses() : List<String> {
        return addressList
    }


    fun getAllTokens(): List<String> {
        return CompletableFuture.supplyAsync({
            val transactions = iotaApi.findTransactionObjectsByAddresses(getAllAddresses().toTypedArray())
            Log.d(TAG, "Found #tokens: "+transactions.size)
            transactions.forEach({
                Log.d(TAG, it.bundle)
            })
            listOf<String>()
        }).get()
    }

}