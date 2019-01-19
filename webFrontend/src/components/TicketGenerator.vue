<template>
  <div>
    <v-app>
    <v-layout row>
      <v-flex xs12 sm12 md10 offset-md1 class="inputContainer">
        <v-card>
          <v-card-text>
            <v-text-field label="ID" placeholder="ID" outline v-model="ticket.id"></v-text-field>
          </v-card-text>
          <v-card-text>
            <v-text-field label="Name" placeholder="Name" outline v-model="ticket.name"></v-text-field>
          </v-card-text>
          <v-card-text>
            <v-text-field label="Place" placeholder="Place" outline v-model="ticket.place"></v-text-field>
          </v-card-text>
          <v-card-text>
            <v-container fluid grid-list-xl>
              <v-layout row justify-space-around>
                <div>
                  <v-btn color="info" class="btnInfo" @click="addTicket">SEND</v-btn>
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
import Ticket from '../bo/Ticket'
import store from '../bo/store'

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
