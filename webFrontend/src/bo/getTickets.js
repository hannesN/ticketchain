import axios from 'axios'
import store from './store'
import Ticket from './Ticket'

function getTickets () {
  store.commit('startLoading')
  axios.get('https://jsonplaceholder.typicode.com/todos/1')
    .then(function (response) {
      store.commit('addTicket', new Ticket(response.data.title, response.data.id))
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

export { getTickets }
