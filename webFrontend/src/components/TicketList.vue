<template>
  <div class="ticketListContainer">
  <v-container grid-list-md text-xs-center>
    <v-layout row wrap>
      <v-flex xs10 offset-xs1 v-for="ticket in tickets" :key="ticket.name">
        <v-card light color="primary">
            <v-card-text class="px-0">
              {{ticket.name}}
                <v-btn flat icon color="blue">
                    <v-icon>send</v-icon>
                </v-btn>
            </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
  </div>
</template>

<script>
export default {
  name: 'TicketList',
  data () {
    return {
      tickets: [{name: 'ticketName'},{name: 'ticketName1'},{name: 'ticketName2'},{name: 'ticketName3'}]
    }
  }
}
</script>

<style scoped>
</style>
