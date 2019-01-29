<template>
  <div>
    <v-app>
      <v-layout row>
        <v-flex xs12 sm12 md10 offset-md1 class="inputContainer">
          <v-card>
            <v-card-text>
              <v-text-field label="Event" outline v-model="ticket.name"></v-text-field>
            </v-card-text>
            <v-card-text>
              <v-text-field label="Location" outline v-model="ticket.location"></v-text-field>
            </v-card-text>
            <v-card-text>
              <v-text-field label="Producer" outline v-model="ticket.producer"></v-text-field>
            </v-card-text>
            <v-card-text>
              <v-text-field label="Amount" outline v-model="ticket.amount"></v-text-field>
            </v-card-text>
            <v-flex xs12 sm12 class="hidden-xs-only">
              <v-date-picker v-model="ticket.date" color="green lighten-1" header-color="primary"></v-date-picker>
            </v-flex>
            <v-card-text>
              <v-container fluid grid-list-xl>
                <v-layout row justify-space-around>
                  <div>
                    <v-btn color="info" class="btnInfo" @click="newTicket(ticket, amount)">SEND</v-btn>
                  </div>
                </v-layout>
              </v-container>
            </v-card-text>
          </v-card>
        </v-flex>
      </v-layout>
    </v-app>
  </div>
</template>

<script>
import {
  addTicket
} from '../bo/sendTickets'
import store from '../bo/store'
import moment from 'moment'

export default {
  name: 'TicketGenerator',
  data () {
    return {
      ticket: {
        name: '',
        location: '',
        producer: '',
        date: '',
        amount: 1
      },
    }
  },
  methods: {
    newTicket (ticket, amount) {
      var ticketToSend = Object.assign({}, ticket)
      ticketToSend.date = moment(ticket.date).toISOString()
      addTicket(ticketToSend)
      store.commit('setTabIndex', 0)
    }
  },
  mounted () {
    store.commit('startLoading')
  }
}
</script>

<style scoped>
  .btnInfo {
    background-color: #2196f3 !important;
    margin-top: -26pt
  }
  .inputContainer {
    padding-top: 16pt
  }
</style>
