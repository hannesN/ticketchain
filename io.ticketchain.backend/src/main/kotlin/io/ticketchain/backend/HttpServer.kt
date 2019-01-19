package io.ticketchain.backend

import spark.Request
import spark.Response
import spark.Spark
import java.text.ParseException
import java.text.SimpleDateFormat

const val GET_TICKET = "/tickets/:event/:date"
const val GET_TICKETS = "/tickets"
const val POST_TICKET = "/tickets"

const val DATE_URL = "yyyyMMddHHmm"
val DATE_URL_FORMATTER = SimpleDateFormat(DATE_URL)
val EMPTY_LIST = mutableListOf<TicketWithToken>()

class HttpServer(val port: Int) {

    fun start() {
        Spark.port(port)

        Spark.post(POST_TICKET) { req, res -> createTickets(req, header(res)) }
        Spark.get(GET_TICKET) { req, res -> getTickets(req, header(res)) }
        Spark.get(GET_TICKETS) { req, res -> getAllTickets(req, header(res)) }
    }

    private fun getAllTickets(req: Request, res: Response): String {
        return TicketConverter.toJson(Iota.findAll())
    }

    private fun header(res: Response): Response {
        println("- ${res}")
        res.header("Access-Control-Allow-Origin", "*")
        return res
    }

    private fun createTickets(req: Request, res: Response): String {
        val json = req.body()
        val creationAction = TicketConverter.fromJson(json)

        val ticketList = mutableListOf<TicketWithToken>()

        (1..creationAction.count).iterator().forEach {
            val ticketWithToken =
                TicketWithToken(creationAction.ticket, Iota.randomToken())

            ticketList.add(ticketWithToken)
        }

        val dateUrl = DATE_URL_FORMATTER.format(creationAction.ticket.date)
        Iota.send("${creationAction.ticket.event}/${dateUrl}", ticketList)

        return TicketConverter.toJson(ticketList)
    }

    private fun getTickets(req: Request, res: Response): String {
        val event = req.params(":event")

        val dateString = req.params(":date")
        val date = try {
            DATE_URL_FORMATTER.parse(dateString)
        } catch (e: ParseException) {
            res.status(500)
            return "Cannot parse '${dateString}' as 'yyyyMMddHHmm' date - e.toString()."
        }

        val ticketList = Iota.find("${event}/${dateString}")

        return TicketConverter.toJson(ticketList)
    }
}
