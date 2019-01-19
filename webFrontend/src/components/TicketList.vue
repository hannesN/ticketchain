<template>
  <div class="ticketListContainer">
    <v-container grid-list-md text-xs-center>
      <v-layout row wrap>
        <v-flex xs12 sm12 md10 offset-md1 v-for="ticket in tickets" :key="ticket.id">
          <v-card light color="primary">
            <v-card-title primary-title>
              <div>
                <div class="headline">{{ticket.name}}</div>
                <span class="grey--text">The Ticket has the ID: {{ticket.id}}</span>
              </div>
            </v-card-title>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn flat icon color="blue">
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
/* eslint-disable */
  import Ticket from '../bo/Ticket.js'
  export default {
    name: 'TicketList',
    data() {
      return {
        tickets: [new Ticket('Konzert Leipzig', '1'),
          new Ticket('Konzert Berlin', '2')
        ]
      }
    }
  }
</script>

<style scoped>
  
</style>
