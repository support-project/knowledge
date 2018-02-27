<template>
  <div class="container container-table">
      <div class="row vertical-10p">
        <div class="container">
          <img src="static/img/human.svg" class="center-block logo">
          <alerts></alerts>
          <div class="text-center col-md-4 col-sm-offset-4">
            <form class="ui form loginForm"  @submit.prevent="checkCreds">
              <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-envelope"></i></span>
                <input class="form-control" name="username" placeholder="Username" type="text" v-model="username">
              </div>
              <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                <input class="form-control" name="password" placeholder="Password" type="password" v-model="password">
              </div>
              <button type="submit" v-bind:class="'btn btn-primary btn-lg'">
                {{ $t('Login.BtnSubmit') }}
              </button>
              <router-link class="btn btn-default" to="/">
                <a>
                  <span class="page">{{ $t('Login.BtnCancel') }}</span>
                </a>
              </router-link>
            </form>
          </div>
        </div>
      </div>
  </div>
</template>

<script>
import Alerts from './views/Parts/Alerts'
import logger from 'logger'

const LABEL = 'Login.vue'

export default {
  name: 'Login',
  components: { Alerts },
  data (router) {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    checkCreds () {
      const {username, password} = this
      this.$store.dispatch('auth/login', {id: username, password: password}).then((result) => {
        logger.debug(LABEL, 'login result: ' + JSON.stringify(result))
        if (result) {
          return this.$store.dispatch('user/loadUserInformation', {
            i18n: this.$i18n
          })
        }
        return result
      }).then((result) => {
        logger.debug(LABEL, 'load user information result: ' + JSON.stringify(result))
        if (result) {
          var redirect = this.$route.query.redirect
          if (!redirect) {
            redirect = '/'
          }
          logger.debug(LABEL, 'redirect: ' + JSON.stringify(redirect))
          this.$router.push(redirect)
        }
      }).catch((e) => {
      })
    }
  }
}
</script>

<style>
html, body, .container-table {
/*  height: 100%; */
/*  background-color: #282B30 !important; */
}
.container-table {
    display: table;
    color: white;
}
.vertical-center-row {
    display: table-cell;
    vertical-align: middle;
}
.vertical-20p {
  padding-top: 20%;
}
.vertical-10p {
  padding-top: 10%;
}
.logo {
  width: 15em;
  padding: 3em;
}
.loginForm .input-group {
  padding-bottom: 1em;
  height: 4em;
}
.input-group input {
  height: 4em;
}
</style>
