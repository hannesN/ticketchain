<template>
  <v-app>
    <v-layout row>
      <v-flex xs12 sm6 offset-sm3>
        <v-card>
          <img src="../assets/icons8-blockchain-96.png">
          <v-card-title primary-title>
            <div>
              <div class="headline">Login to your wallet</div>
              <span class="grey--text"></span>
            </div>
          </v-card-title>
          <v-card-text>
            <v-text-field label="Username" placeholder="Username" outline v-model="username"></v-text-field>
            <v-text-field label="Password" placeholder="Password" outline v-model="password" type="password"></v-text-field>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn flat color="warning" v-on:click="login">Cancle</v-btn>
            <v-btn flat color="success" v-on:click="login">Login</v-btn>
          </v-card-actions>
        </v-card>
      </v-flex>
    </v-layout>
  </v-app>
</template>

<script>
import store from '../bo/store'

export default {
  name: 'LoginPage',
  data () {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    login () {
      store.commit('login')
    }
  }
}
</script>

<style scoped>
</style>
