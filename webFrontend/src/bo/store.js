import Vuex from 'vuex'
import Vue from 'vue'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    tickets: [],
    showSnackBar: false,
    snackBarText: '',
    loggedIn: false,
    tabIndex: 0
  },
  mutations: {
    addTicket (store, ticket) {
      store.tickets = ticket
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
    },
    setTabIndex (store, tabIndex) {
      store.tabIndex = tabIndex
    }
  }
})
