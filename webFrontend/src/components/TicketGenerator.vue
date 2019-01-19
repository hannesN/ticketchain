<template>
  <div>
    <v-app>
      <v-layout row>
        <v-flex xs12 sm12 md10 offset-md1 class="inputContainer">
          <v-card>
            <v-card-text>
              <v-text-field label="Event" outline v-model="ticket.event"></v-text-field>
            </v-card-text>
            <v-card-text>
              <v-text-field label="Location" outline v-model="ticket.location"></v-text-field>
            </v-card-text>
            <v-card-text>
              <v-text-field label="Promoter" outline v-model="ticket.promoter"></v-text-field>
            </v-card-text>
            <v-flex xs12 sm6 class="hidden-xs-only">
              <v-date-picker v-model="ticket.promoter" color="green lighten-1" header-color="primary"></v-date-picker>
            </v-flex>
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
import store from '../bo/store'

export default {
  name: 'TicketGenerator',
  data () {
    return {
      ticket: {
        event: '',
        location: '',
        promoter: '',
        date: ''
      }
    }
  },
  methods: {
    addTicket () {
      addTicket(this.ticket)
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
