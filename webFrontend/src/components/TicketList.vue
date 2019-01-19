<template>
  <div class="ticketListContainer">
    <v-container grid-list-md text-xs-center>
      <v-layout row wrap>
        <v-flex xs12 sm12 md10 offset-md1 v-for="ticket in tickets" :key="ticket.id" v-bind="ticket">
          <v-card light color="primary">
            <v-card-title primary-title>
              <div>
                <div class="headline">{{ticket.name}}</div>
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
      </v-layout>
    </v-container>
  </div>
</template>

<script>
import store from '../bo/store'
import {
  addTicket
} from '../bo/sendTickets'

export default {
  name: 'TicketList',
  computed: {
    tickets () {
      return store.state.tickets
    }
  },
  methods: {
    addTicket: addTicket
  }
}
</script>
<style scoped>
</style>
