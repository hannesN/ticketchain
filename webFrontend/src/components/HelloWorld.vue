<template>
  <div>
    <v-toolbar color="cyan" dark tabs>
      <v-toolbar-title>{{ msg }}</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon>
        <v-icon>more_vert</v-icon>
      </v-btn>
      <v-tabs slot="extension" v-model="tab" color="cyan" grow>
        <v-tabs-slider color="yellow"></v-tabs-slider>
        <v-tab v-for="item in items" :key="item">
          {{ item }}
        </v-tab>
      </v-tabs>
    </v-toolbar>
    <v-tabs-items v-model="tab">
      <ticket-list v-if="parseInt(tab)===0" />
      <ticket-generator v-if="parseInt(tab)===1" />
    </v-tabs-items>
  </div>
</template>

<script>
import TicketList from './TicketList.vue'
import TicketGenerator from './TicketGenerator.vue'
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Welcome to Your Wallet',
      tab: null,
      items: [
        'Your Wallet', 'New Ticket'
      ],
      text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'
    }
  },
  components: {
    TicketList,
    TicketGenerator
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
