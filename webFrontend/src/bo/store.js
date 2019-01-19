import Vuex from 'vuex'
import Vue from 'vue'
import Ticket from './Ticket'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    count: 0,
    tickets: [new Ticket('Konzert Leipzig', '1'),
      new Ticket('Konzert Berlin', '2')
    ]
  },
  mutations: {
    addTicket (store, ticket) {
      store.tickets.push(ticket)
    }
  }
})
