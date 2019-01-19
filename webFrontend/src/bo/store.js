import Vuex from 'vuex'
import Vue from 'vue'
import Ticket from './Ticket'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    tickets: [new Ticket('Konzert Leipzig', '1'),
      new Ticket('Konzert Berlin', '2')
    ],
    showSnackBar: false,
    snackBarText: '',
    loggedIn: false
  },
  mutations: {
    addTicket (store, ticket) {
      store.tickets.push(ticket)
    },
    startLoading (store) {
      store.showSnackBar = false
    },
    finishLoadingSuccessfully (store) {
      store.snackBarText = 'Successfully load new Data'
      store.showSnackBar = true
    },
    finishLoadingWithError (store) {
      store.snackBarText = 'Error while loading new Data'
      store.showSnackBar = true
    },
    login (store) {
      store.loggedIn = true
    }
  }
})
