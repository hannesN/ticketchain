import store from './store'
import axios from 'axios'
import Ticket from './Ticket'

function addTicket (ticket) {
  axios.post('http://192.168.16.150:8080/tickets', ticket)
    .then(function (response) {
      store.commit('addTicket', new Ticket(response.data.token, response.data.event, response.data.location, response.data.promoter, response.data.date))
      store.commit('finishLoadingSuccessfully')
    })
    .catch(function (error) {
      console.log(error)
      store.commit('finishLoadingWithError')
    })
}

export {
  addTicket
}
