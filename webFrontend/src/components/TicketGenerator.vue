<template>
  <div>
    <v-form>
      <v-container>
        <v-layout row justify-space-around>
          <v-flex xs12 sm6 md3>
            <v-text-field label="ID" placeholder="ID" outline v-model="ticket.id"></v-text-field>
          </v-flex>
          <v-flex xs12 sm6 md3>
            <v-text-field label="Name" placeholder="Name" outline v-model="ticket.name"></v-text-field>
          </v-flex>
          <v-flex xs12 sm6 md3>
            <v-text-field label="Place" placeholder="Place" outline v-model="ticket.place"></v-text-field>
          </v-flex>
        </v-layout>
      </v-container>
    </v-form>
    <v-container fluid grid-list-xl>
      <v-layout row justify-space-around>
        <v-flex xs2>
          <div>
            <v-btn color="info" class="btnInfo" @click="addTicket">SEND</v-btn>
          </div>
        </v-flex>
      </v-layout>
    </v-container>
  </div>
</template>

<script>
import {
  addTicket
} from '../bo/sendTickets'
import Ticket from '../bo/Ticket'

export default {
  name: 'TicketGenerator',
  data () {
    return {
      ticket: {
        name: '',
        id: '',
        place: ''
      }
    }
  },
  methods: {
    addTicket () {
      var ticketNew = new Ticket(this.ticket.name, this.ticket.id)
      addTicket(ticketNew)
    }
  }
}
</script>

<style scoped>
.btnInfo {
  background-color: #2196f3 !important
}
</style>
