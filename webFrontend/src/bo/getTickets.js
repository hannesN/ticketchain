import axios from 'axios'
import store from './store'
import Ticket from './Ticket'
import moment from 'moment'

function getTickets () {
  store.commit('startLoading')
  axios.get('http://localhost:8080/eventsfromchain')
    .then(function (response) {
      var newTickets = []
      response.data.forEach(ticket => {
        var ticketData = ticket
        var date = moment(ticketData.date)
        newTickets.push(new Ticket(ticket.id, ticketData.name, ticketData.location, ticketData.producer, date))
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
