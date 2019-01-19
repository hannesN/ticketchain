package io.ticketchain.wallet.iota

import android.util.Log
import jota.IotaAPI
import jota.dto.response.SendTransferResponse
import jota.model.Transfer
import jota.utils.Converter
import jota.utils.TrytesConverter
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier

class Transaction(
    iotaClient: IotaAPI,
    private val sender: Wallet,
    private val receiver: Wallet
) : AbstractIotaConnector(iotaClient) {

    private val DEPTH: Int = 9

    private val MIN_WEIGHT_MAGNITUDE: Int = 14

    private val TAG: String = "Transaction"

    fun sendMessage(msg: String) {
        val receiverAddress = receiver.getValidAdress()
        Log.d(TAG, "Receiver Address: "+receiverAddress)
        val transfer = Transfer(
            receiverAddress,
            0,
            TrytesConverter.asciiToTrytes(msg),
            "IOTAJAVASPAM999999999999999"

        )

        val response = CompletableFuture.supplyAsync({
            iotaClient.sendTransfer(
                sender.id,
                2,
                DEPTH,
                MIN_WEIGHT_MAGNITUDE,
                listOf(transfer),
                null,
                null,
                false,
                true,
                null
            )
        }).get()


//        val response = runOnNetworkThread<SendTransferResponse>({
//            iotaClient.sendTransfer(
//                sender.id,
//                2,
//                DEPTH,
//                MIN_WEIGHT_MAGNITUDE,
//                listOf(transfer),
//                null,
//                null,
//                false,
//                true,
//                null
//            )
//        })

        Log.d(TAG, "TransactionAddress: "+response.transactions.first().address)
        Log.d(TAG, "Transfer Response: " + response.successfully.joinToString { it.toString() + "," })


    }
}