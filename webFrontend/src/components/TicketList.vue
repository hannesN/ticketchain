<template>
  <div class="ticketListContainer">
    <v-app>
      <v-container grid-list-xl text-xs-center>
        <v-layout row wrap>
          <v-flex xs12 sm12 md10 offset-md1 v-for="ticket in tickets" :key="ticket.id" v-bind="ticket">
            <v-card light>
              <v-card-title primary-title>
                <div>
                  <div class="headline">{{ticket.event}}</div>
                  <span class="grey--text">The Ticket has the ID: {{ticket.id}}</span>
                </div>
              </v-card-title>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn flat icon color="blue" @click="addTicket(ticket)">
                  <v-icon>send</v-icon>
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-flex>
          <v-flex xs12 sm12 md10 offset-md1 v-if="tickets.length === 0">
            <v-card light>
              <v-card-title primary-title>
                <div>
                  <div class="headline">{{emptyTicket.event}}</div>
                  <span class="grey--text">The Ticket has the ID: {{emptyTicket.id}}</span>
                </div>
              </v-card-title>
            </v-card>
          </v-flex>
          <v-container fluid grid-list-xl>
            <v-layout row justify-space-around>
              <div>
                <v-btn color="info" class="btnInfo" @click="getTickets">REFRESH</v-btn>
              </div>
            </v-layout>
          </v-container>
        </v-layout>
      </v-container>
      <v-snackbar v-model="snackbar" color="info" :right="true" :timeout="1000" :top="true">
        {{ snackbarText }}
      </v-snackbar>
    </v-app>
  </div>
</template>

<script>
import store from '../bo/store'
import {
  addTicket
} from '../bo/sendTickets'
import {
  getTickets
} from '../bo/getTickets'
import Ticket from '../bo/Ticket'

export default {
  name: 'TicketList',
  data () {
    return {
      emptyTicket: new Ticket(0, 'no event set', 'no location', 'no promoter', 'no date')
    }
  },
  computed: {
    tickets () {
      return store.state.tickets
    },
    snackbar () {
      return store.state.showSnackBar
    },
    snackbarText () {
      return store.state.snackBarText
    }
  },
  methods: {
    addTicket: addTicket,
    getTickets: getTickets
  }
}
</script>

<style scoped>
.btnInfo {
  background-color: #2196f3 !important;
}
</style>
