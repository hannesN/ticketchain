package io.ticketchain.wallet.ether

import jota.IotaAPI
import java.util.concurrent.CompletableFuture
import java.util.function.Supplier

abstract class AbstractIotaConnector(val iotaClient: IotaAPI) {

    protected fun <R> runOnNetworkThread(runnable: Supplier<R>): R {
        val future: CompletableFuture<R> = CompletableFuture.supplyAsync(runnable)

        while (!future.isDone) {
            Thread.sleep(50)
        }
        return future.get()
    }

}