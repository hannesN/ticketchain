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
                </div>
              </v-card-title>
              <v-card-text>
                <p class="grey--text text-sm-left">The Token-ID is: {{ticket.id}}</p>
                <p class="grey--text text-sm-left">The Eventlocation is: {{ticket.location}}</p>
                <p class="grey--text text-sm-left">The Eventpromoter is: {{ticket.promoter}}</p>
                <p class="grey--text text-sm-left">The Date is: {{ticket.date.format('DD/MM/YYYY')}}</p>
              </v-card-text>
              <v-card-actions class="btnContainer">
                <v-spacer></v-spacer>
                <v-btn flat icon color="blue" @click="openDialogForTicket(ticket)">
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
      <!-- dialog -->
      <v-dialog v-model="dialog" max-width="500px">
        <v-card>
          <v-card-title>
            Send Ticket
          </v-card-title>
          <v-card-text>
            <v-flex xs12 sm12 md12>
              <v-text-field label="Targetaddress" v-model="targetAddress"></v-text-field>
            </v-flex>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn flat icon color="blue" @click="sendTicket">
              <v-icon>send</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
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
      emptyTicket: new Ticket(0, 'no event set', 'no location', 'no promoter', 'no date'),
      dialog: false,
      targetAddress: '',
      ticketToSend: {}
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
    getTickets: getTickets,
    openDialogForTicket (ticket) {
      this.ticketToSend = ticket
      this.dialog = true
    },
    sendTicket () {
      console.log(JSON.stringify(this.ticketToSend))
      console.log(this.targetAddress)
      this.dialog = false
    }
  },
  mounted () {
    getTickets()
  }
}
</script>

<style scoped>
.btnInfo {
  background-color: #2196f3 !important;
}

.btnContainer {
  margin-top: -40pt !important;
}

p {
  margin-bottom: 6px !important;
  word-break: break-all;
  white-space: normal;
}
</style>
