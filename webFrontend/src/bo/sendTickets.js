import store from './store'
import axios from 'axios'
// import Ticket from './Ticket'
// import moment from 'moment'
import {getTickets} from './getTickets'

function addTicket (ticket) {
  let axiosConfig = {
    headers: {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    }
  }
  axios.post('http://localhost:8080/events/new', JSON.stringify(ticket), axiosConfig)
    .then(function (response) {
      getTickets()
      store.commit('finishLoadingSuccessfully')
    })
    .catch(function (error) {
      console.log(error)
      store.commit('finishLoadingWithError')
    })
}

//0x67dE66d863e2a35b4dD239162896166198bFe223
function sendTicket (eventID, receiver) {
  let axiosConfig = {
    headers: {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    }
  }
  var ticket = {
    event: eventID,
    receiver: receiver
  }
  axios.post('http://localhost:8080/events/sendticket', JSON.stringify(ticket), axiosConfig)
    .then(function (response) {
      getTickets()
      store.commit('finishLoadingSuccessfully')
    })
    .catch(function (error) {
      console.log(error)
      store.commit('finishLoadingWithError')
    })
}

export {
  addTicket,
  sendTicket
}
