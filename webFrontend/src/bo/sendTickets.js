import store from './store'
import axios from 'axios'
// import Ticket from './Ticket'
// import moment from 'moment'
import {getTickets} from './getTickets'

function addTicket (ticket, amount) {
  var postData = Object.assign({}, {
    ticket: ticket, count: amount
  })
  console.log(JSON.stringify(postData))
  let axiosConfig = {
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      'Access-Control-Allow-Origin': '*'
    }
  }
  axios({
    method: 'post',
    url: 'http://192.168.16.150:8080/tickets',
    data: JSON.stringify(postData),
    header: axiosConfig
  })
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
  addTicket
}
