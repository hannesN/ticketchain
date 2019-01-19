import store from './store'

function addTicket (ticket) {
  store.commit('addTicket', ticket)
}

export { addTicket }
