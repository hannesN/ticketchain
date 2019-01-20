package io.ticketchain.backend

import org.junit.Test
import java.util.*

class IotaTest {
    @Test
    fun firstTest() {
        val transfer =
            Iota.createTransferToMe(Ticket("event-name", "event-location", "event-promoter", Date()))

        val address = Iota.send(transfer)
        val transaction = Iota.find(address)
        println(transaction)
    }

    @Test
    fun nextAddressTest(){
        println(Iota.createAddress().address)
    }

    @Test
    fun checkBalanceTest(){
        val test = "ET9JXYTDHXIGEFZUUZASEQSKGCHMCSLYVPBYFCV9UNER9FQPEWPIVQQNYKRDSWBESYTVFFBXMQTHFGFP9"
        val blanceAddress = "KANORMENBQSBOEGNVKMZMFQA9CBJYJBKLELRCRKFEIJLWGUACAG9AEEIIWITMJBAYAZZBBOWHIPAXAEGB"
        //"Your allocation request has been successfully processed with aadea812-fbc3-4e05-a87e-0e73f4bab46f. Please check your balance in a while."
        val transaction = Iota.find(IotaPublicAddress(blanceAddress))
        println(transaction)

        val msg = Iota.decodeMessage(transaction!!)

        Iota.getBalance()
    }

    @Test
    fun test(){
        val ticket = Ticket("event", "location", "promoter", Date())
        val createTransfer = Iota.createTransfer(
            ticket,
            IotaPublicAddress("W9PEEQPKCRKASCNWDAKPEFNWPCWYAB9WJRZLGMNKNIBVSZSTPWUWEVLAQABR9BZLDZLUXLRGQIOQLISFX")
        )
        val send = Iota.send(createTransfer)
        println(send)
    }
}
