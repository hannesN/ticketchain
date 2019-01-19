<template>
  <div>
    <v-app v-if="loggedIn">
    <v-toolbar color="blue" dark tabs>
      <v-toolbar-title>{{ msg }}</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon>
        <v-icon>more_vert</v-icon>
      </v-btn>
      <v-tabs slot="extension" v-model="tab" color="blue" grow>
        <v-tabs-slider color="green"></v-tabs-slider>
        <v-tab v-for="item in items" :key="item">
          {{ item }}
        </v-tab>
      </v-tabs>
    </v-toolbar>
    <v-tabs-items v-model="tab">
      <ticket-list v-if="parseInt(tab)===0" />
      <ticket-generator v-if="parseInt(tab)===1" />
    </v-tabs-items>
  </v-app>
  <v-app v-if="!loggedIn">
    <login-page/>
  </v-app>
  </div>
</template>

<script>
import TicketList from './TicketList.vue'
import TicketGenerator from './TicketGenerator.vue'
import LoginPage from './LoginPage.vue'
import store from '../bo/store'
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Welcome to Your Wallet',
      tab: null,
      items: [
        'Your Wallet', 'New Ticket'
      ]
    }
  },
  computed: {
    loggedIn () {
      return store.state.loggedIn
    }
  },
  components: {
    TicketList,
    TicketGenerator,
    LoginPage
  }
}
</script>

<style scoped>
h1,
h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
