import axios from 'axios'
import store from './store'
import Ticket from './Ticket'

function getTickets () {
  store.commit('startLoading')
  axios.get('http://192.168.16.150:8080/tickets/test/201901192000')
    .then(function (response) {
      store.commit('addTicket', new Ticket(response.data.id, response.data.title, 'location', 'promoter', new Date()))
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
