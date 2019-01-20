import axios from 'axios'
import store from './store'
import Ticket from './Ticket'
import moment from 'moment'

function getTickets () {
  store.commit('startLoading')
  axios.get('http://192.168.16.150:8080/tickets')
    .then(function (response) {
      var newTickets = []
      response.data.forEach(ticket => {
        var ticketData = ticket.ticket
        var date = moment(ticketData.date)
        newTickets.push(new Ticket(ticket.token, ticketData.event, ticketData.location, ticketData.promoter, date))
      })
      store.commit('addTicket', newTickets)
      store.commit('finishLoadingSuccessfully')
    })
    .catch(function (error) {
      console.log(error)
      store.commit('finishLoadingWithError')
    })
    .then(function () {
      // always executed
    })
}

export {
  getTickets
}
