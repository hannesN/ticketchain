package io.ticketchain.backend

import jota.IotaAPI
import jota.dto.response.GetBalancesResponse
import jota.model.Transaction
import jota.model.Transfer
import jota.utils.TrytesConverter
import java.math.BigInteger
import java.util.*
import java.util.concurrent.CompletableFuture

const val IOTA_NODE_PROTOCOL = "https"
const val IOTA_NODE = "altnodes.devnet.iota.org"
const val IOTA_NODE_PORT = "443"

const val SEED = "SERVERBEF9KJDBTZLOHSBCJSTJJMABVC9HJBGZIDAHFVMNFQAXGAHJKF9VCGHCFGJCGHFNKLGFEGHJKS9"
const val DEFAULT_ADDRESS_TO_CREATE_COUNT = 1
const val DEFAULT_ADDRESS_WITH_CHECKSUM = false
const val DEFAULT_ADDRESS_SECURITY_LEVEL = 2

const val IOTA_TRANSFER_VALUE: Long = 0
const val IOTA_TRANFER_TAG = "TICKETCHAINITEMISBERLIN9999"

const val IOTA_TRANSFER_DEPTH: Int = 9
const val IOTA_TRANSFER_MIN_WEIGHT_MAGNITUDE: Int = 14
const val IOTA_TRANSFER_SECURITY = 2
const val IOTA_TRANSFER_VALIDATE_INPUTS = false
const val IOTA_TRANSFER_VALIDATE_INPUT_ADDRESS = true

val IOTA_TRANSFER_INPUTS = null
val IOTA_TRANSFER_REMAINDER_ADDRESS = null
val IOTA_TRANSFER_TIPS = null

data class IotaPublicAddress(val address: String)

class Iota {
    companion object {
        val iota = IotaAPI.Builder()
            .protocol(IOTA_NODE_PROTOCOL)
            .host(IOTA_NODE)
            .port(IOTA_NODE_PORT)
            .build()

        private val ticketMap = mutableMapOf<String, List<TicketWithToken>>()
        private val ticketByAddressMap = mutableMapOf<IotaPublicAddress, TicketWithToken>()

        fun randomToken(): String {
            return Trytes.randomSequenceOfLength(81)
        }

        fun send(eventKey: String, tickets: MutableList<TicketWithToken>) {
            ticketMap.put(eventKey, tickets)

            tickets.forEach {
                CompletableFuture.supplyAsync {
                    val transfer = createTransferToMe(it.ticket)
                    println("$transfer.address - $it")
                    ticketByAddressMap.put(IotaPublicAddress(transfer.address), it)
                    send(transfer)
                }
            }
        }

        fun find(eventKey: String): List<TicketWithToken> {
            val ticketList = ticketMap.get(eventKey) ?: EMPTY_LIST
            if (ticketList.isEmpty()) {
                println(ticketMap)
            }
            return ticketList
        }

        fun createAddress(): IotaPublicAddress {
            val newAddresses = iota.generateNewAddresses(
                SEED,
                DEFAULT_ADDRESS_SECURITY_LEVEL,
                DEFAULT_ADDRESS_WITH_CHECKSUM,
                DEFAULT_ADDRESS_TO_CREATE_COUNT
            )
            return IotaPublicAddress(newAddresses.first())
        }

        fun createTransferToMe(ticket: Ticket): Transfer {
            return createTransfer(ticket, createAddress())
        }

        fun createTransfer(ticket: Ticket, to: IotaPublicAddress): Transfer {
            val ticketAsTrytes = TrytesConverter.asciiToTrytes(TicketConverter.toJson(ticket))
            return Transfer(
                to.address,
                499,
                ticketAsTrytes,
                IOTA_TRANFER_TAG
            )
        }

        fun send(transfer: Transfer): IotaPublicAddress {
            iota.sendTransfer(
                SEED,
                IOTA_TRANSFER_SECURITY,
                IOTA_TRANSFER_DEPTH,
                IOTA_TRANSFER_MIN_WEIGHT_MAGNITUDE,
                listOf(transfer),
                IOTA_TRANSFER_INPUTS,
                IOTA_TRANSFER_REMAINDER_ADDRESS,
                IOTA_TRANSFER_VALIDATE_INPUTS,
                IOTA_TRANSFER_VALIDATE_INPUT_ADDRESS,
                IOTA_TRANSFER_TIPS
            )
            return IotaPublicAddress(transfer.address)
        }

        fun findAll(): List<TicketWithToken> {
            return ticketMap.values.flatMap { it }
        }

        fun find(address: IotaPublicAddress): Transaction? {
            val transactions =
                iota.findTransactionObjectsByAddresses(listOf(address.address).toTypedArray())
            if(transactions.isEmpty()) {
                return null
            }
            return transactions.first()
        }

        fun getBalance(): GetBalancesResponse? {
            val balances = iota.getBalances(1, listOf(SEED))
            return balances
        }

        fun decodeMessage(transaction: Transaction): String {
            return TrytesConverter.trytesToAscii(transaction.signatureFragments.replace("9*$".toRegex(),""))
        }
    }
}

object Trytes {
    val NULL_HASH = fromTrits(ByteArray(81 * 3))
    val TRYTES = "9ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val BI3 = BigInteger.valueOf(3)
    internal val MAX_TRYTE_TRIPLET_ABS = 9841 // 9841 = (3^9-1)/2

    val TRITS_BY_TRYTE = arrayOf(
        byteArrayOf(0, 0, 0), byteArrayOf(1, 0, 0), byteArrayOf(-1, 1, 0), //9AB
        byteArrayOf(0, 1, 0), byteArrayOf(1, 1, 0), byteArrayOf(-1, -1, 1), //CDE
        byteArrayOf(0, -1, 1), byteArrayOf(1, -1, 1), byteArrayOf(-1, 0, 1), //FGH
        byteArrayOf(0, 0, 1), byteArrayOf(1, 0, 1), byteArrayOf(-1, 1, 1), //IJK
        byteArrayOf(0, 1, 1), byteArrayOf(1, 1, 1), byteArrayOf(-1, -1, -1), //LMN
        byteArrayOf(0, -1, -1), byteArrayOf(1, -1, -1), byteArrayOf(-1, 0, -1), //OPQ
        byteArrayOf(0, 0, -1), byteArrayOf(1, 0, -1), byteArrayOf(-1, 1, -1), //RST
        byteArrayOf(0, 1, -1), byteArrayOf(1, 1, -1), byteArrayOf(-1, -1, 0), //UVW
        byteArrayOf(0, -1, 0), byteArrayOf(1, -1, 0), byteArrayOf(-1, 0, 0) //XYZ
    )

    fun toNumber(trytes: String): BigInteger {
        val trits = toTrits(trytes)
        var number = BigInteger.ZERO
        for (i in trits.indices.reversed())
            number = number.multiply(BI3).add(BigInteger.valueOf(trits[i].toLong()))
        return number
    }

    fun toTrits(trytes: String): ByteArray {
        val trits = ByteArray(trytes.length * 3)
        for (i in 0 until trytes.length) {
            val tritTriplet = toTrits(trytes[i])
            System.arraycopy(tritTriplet, 0, trits, 3 * i, 3)
        }
        return trits
    }

    fun sumTrytes(trytes: String): Int {
        var sum = 0
        for (c in trytes.toCharArray()) {
            val tritTriplet = toTrits(c)
            sum += tritTriplet[0].toInt() + 3 * tritTriplet[1] + 9 * tritTriplet[2]
        }
        return sum
    }

    private fun toTrits(tryte: Char): ByteArray {
        return TRITS_BY_TRYTE[TRYTES.indexOf(tryte, 0)]
    }

    fun fromTrits(trits: ByteArray): String {
        assert(trits.size % 3 == 0)
        val trytes = ByteArray(trits.size / 3)
        for (i in trytes.indices)
            trytes[i] = tryteFromTrits(Arrays.copyOfRange(trits, i * 3, i * 3 + 3), 0)
        return String(trytes)
    }

    private fun tryteFromTrits(trits: ByteArray, offset: Int): Byte {
        val index = trits[offset + 0].toInt() + 3 * trits[offset + 1] + 9 * trits[offset + 2]
        return TRYTES[(index + 27) % 27].toByte()
    }

    fun toLong(trytes: String): Long {
        return toNumber(trytes).toLong()
    }

    fun fromAscii(ascii: String): String {
        var ascii = ascii
        assert(toNumber(fromNumber(BigInteger.valueOf(9245), 3)).toLong() == 9245L)

        if (ascii.length % 2 != 0) {
            ascii += 0.toChar()
        }
        val trytes = CharArray(ascii.length / 2 * 3)
        var i = 0
        while (i + 1 < ascii.length) {
            val intVal = ascii[i].toInt() * 127 + ascii[i + 1].toInt() - MAX_TRYTE_TRIPLET_ABS
            val tryteTriplet = fromNumber(BigInteger.valueOf(intVal.toLong()), 3)
            trytes[i / 2 * 3 + 0] = tryteTriplet[0]
            trytes[i / 2 * 3 + 1] = tryteTriplet[1]
            trytes[i / 2 * 3 + 2] = tryteTriplet[2]
            i += 2
        }
        return String(trytes)
    }

    fun toAscii(trytes: String): String {
        var trytes = trytes
        // unpad 9 triplets
        trytes = unpadRight(trytes)
        trytes = padRight(trytes, trytes.length + 2 - (trytes.length + 2) % 3)
        assert(trytes.length % 3 == 0)

        // convert tryte triplets to ascii tuples
        val ascii = CharArray(trytes.length / 3 * 2)
        for (i in 0 until trytes.length / 3) {
            val tryteTriplet = trytes.substring(3 * i, 3 * i + 3)
            val intVal = toNumber(tryteTriplet).toInt() + MAX_TRYTE_TRIPLET_ABS
            ascii[2 * i + 0] = (intVal / 127).toChar()
            ascii[2 * i + 1] = (intVal % 127).toChar()
        }
        val evenSizedAscii = String(ascii)
        return if (evenSizedAscii.length > 0 && evenSizedAscii[evenSizedAscii.length - 1].toInt() == 0)
            evenSizedAscii.substring(0, evenSizedAscii.length - 1)
        else
            evenSizedAscii
    }

    fun padRight(trytes: String, length: Int): String {
        val padded = CharArray(length)
        System.arraycopy(trytes.toCharArray(), 0, padded, 0, trytes.length)
        for (i in trytes.length until length)
            padded[i] = '9'
        return String(padded)
    }

    fun isTrytes(string: String): Boolean {
        return string.matches("^[A-Z9]*$".toRegex())
    }


    internal fun unpadRight(padded: String): String {
        var cutPos: Int
        cutPos = padded.length
        while (cutPos > 0 && padded[cutPos - 1] == '9') {
            cutPos--
        }
        return padded.substring(0, cutPos)
    }

    fun randomSequenceOfLength(length: Int): String {
        val sequence = CharArray(length)
        for (i in 0 until length)
            sequence[i] = randomTryte()
        return String(sequence)
    }

    fun toBytes(trytes: String): ByteArray {
        assert(trytes.length % 3 == 0)
        val bytes = ByteArray(trytes.length / 3 * 2)
        for (i in 0 until trytes.length / 3) {
            val nineTrits = toTrits(trytes.substring(3 * i, 3 * i + 3))
            bytes[2 * i + 0] = tritsToByte(nineTrits, 0, 5)
            bytes[2 * i + 1] = tritsToByte(nineTrits, 5, 4)
        }
        return bytes
    }

    fun fromBytes(bytes: ByteArray): String {
        assert(bytes.size % 2 == 0)
        val trytes = ByteArray(bytes.size / 2 * 3)
        for (i in 0 until trytes.size / 3) {
            val nineTrits = ByteArray(9)
            byteToTrits(bytes[2 * i + 0], nineTrits, 0, 5)
            byteToTrits(bytes[2 * i + 1], nineTrits, 5, 4)
            trytes[3 * i + 0] = tryteFromTrits(nineTrits, 0)
            trytes[3 * i + 1] = tryteFromTrits(nineTrits, 3)
            trytes[3 * i + 2] = tryteFromTrits(nineTrits, 6)
        }
        return String(trytes)
    }

    internal fun tritsToByte(trits: ByteArray, offset: Int, length: Int): Byte {
        var sum = 0
        var exp = 1
        for (i in offset until offset + length) {
            sum += (exp * trits[i]).toByte()
            exp *= 3
        }
        return sum.toByte()
    }

    internal fun byteToTrits(b: Byte, target: ByteArray, offset: Int, length: Int) {
        var n = Math.abs(b.toInt()).toByte()
        for (i in 0 until length) {
            val quotient = n / 3
            val remainder = n % 3
            if (remainder > 1) {
                target[i + offset] = (if (b >= 0) 255 else 1).toByte()
                n = (quotient + 1).toByte()
            } else {
                target[i + offset] = (if (b >= 0) remainder else -remainder).toByte()
                n = quotient.toByte()
            }
        }
    }

    fun fromNumber(value: BigInteger, tryteLength: Int): String {
        assert(value.abs().toLong() <= (Math.pow(3.0, (tryteLength * 3).toDouble()) - 1) / 2)
        val trits = ByteArray(tryteLength * 3)

        var number = value.abs()

        for (i in 0 until tryteLength * 3) {
            val divisionResult = number.divideAndRemainder(BI3)
            val quotient = divisionResult[0]
            val remainder = divisionResult[1]
            if (remainder.compareTo(BigInteger.ONE) > 0) {
                trits[i] = (if (value.signum() >= 0) -1 else 1).toByte()
                number = quotient.add(BigInteger.ONE)
            } else {
                trits[i] = ((if (value.signum() >= 0) 1 else -1) * remainder.toByte()).toByte()
                number = quotient
            }
        }

        return fromTrits(trits)
    }

    private fun randomTryte(): Char {
        return Trytes.TRYTES[(Math.random() * 27).toInt()]
    }
}