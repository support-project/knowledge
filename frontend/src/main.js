/* global $ */
import logger from 'logger'
if (process && process.env && process.env.LOG_LEVEL) {
  logger.setLevel(process.env.LOG_LEVEL)
}

// Import ES6 Promise
import 'es6-promise/auto'

// Import System requirements
import Vue from 'vue'
import VueRouter from 'vue-router'
import VueI18n from 'vue-i18n'
import VueLazyload from 'vue-lazyload'

import { sync } from 'vuex-router-sync'
import routes from './routes'
import store from './store'

// Import Helpers for filters
import { domain, count, prettyDate, pluralize } from './filters'

// Import Views - Top level
import AppView from './components/App.vue'

var LABEL = 'main.js'

var serverURI = $('base').attr('href')
serverURI = serverURI.substring(0, serverURI.length - 1)
// Import Install and register helper items
Vue.filter('count', count)
Vue.filter('domain', domain)
Vue.filter('prettyDate', prettyDate)
Vue.filter('pluralize', pluralize)

Vue.use(VueRouter)

// Routing logic
var router = new VueRouter({
  routes: routes,
  mode: 'history',
  linkExactActiveClass: 'active',
  scrollBehavior: function (to, from, savedPosition) {
    return savedPosition || { x: 0, y: 0 }
  }
})

// Some middleware to help us ensure the user is authenticated.
router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth) && (!router.app.$store.state.token || router.app.$store.state.token === 'null')) {
    // this route requires auth, check if logged in
    // if not, redirect to login page.
    window.console.log('Not authenticated')
    next({
      path: '/login',
      query: { redirect: to.fullPath }
    })
  } else {
    next()
  }
})

sync(store, router)

// Check local storage to handle refreshes
if (window.localStorage) {
  var localUserString = window.localStorage.getItem('user') || 'null'
  var localUser = JSON.parse(localUserString)
  logger.debug(LABEL, localUser)
  if (localUser && store.state.user !== localUser) {
    // TODO Tokenが有効かどうかのチェック（無効になっていれば、ログアウト）

    store.commit('SET_USER', localUser)
    store.commit('SET_TOKEN', window.localStorage.getItem('token'))
  } else {
    store.commit('SET_USER', {
      avatar: 'open.account/icon/',
      userName: 'anonymous'
    })
  }
}

Vue.use(VueLazyload, {
  preLoad: 1.3,
  error: './static/img/loader.gif',
  loading: './static/img/loader.gif',
  attempt: 1
})

const messageEn = require('../static/resource/message-en.json')
const messageJa = require('../static/resource/message-ja.json')
const message = {
  en: messageEn,
  ja: messageJa
}

// 言語の設定
Vue.use(VueI18n)
const i18n = new VueI18n({
  locale: 'ja',
//  locale: 'en',
  fallbackLocale: 'en',
  messages: message
})

store.dispatch('setServerURI', serverURI).then(() => {
  // Start out app!
  // eslint-disable-next-line no-new
  new Vue({
    el: '#root',
    router: router,
    store: store,
    render: h => h(AppView),
    i18n: i18n,
    beforeCreate: function () {
      var _self = this

      // TODO Ajax access to get user locale config
      setTimeout(function () {
        _self.$i18n.locale = 'en'
      }, 100)
    }
  })
})
