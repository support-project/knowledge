/* global $ */
import logger from 'logger'
if (process && process.env && process.env.LOG_LEVEL) {
  logger.setLevel(process.env.LOG_LEVEL)
  logger.setCallSite(process.env.LOG_CALLSITE)
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

import rightSidebar from './lib/displayParts/rightSidebar'

import lang from 'lang'
import moment from 'moment'

var LABEL = 'main.js'

var serverURI = $('base').attr('href')
serverURI = serverURI.substring(0, serverURI.length - 1)
// Import Install and register helper items
Vue.filter('count', count)
Vue.filter('domain', domain)
Vue.filter('prettyDate', prettyDate)
Vue.filter('pluralize', pluralize)
Vue.filter('abbreviate', function (value, len) {
  return lang.abbreviate(value, len)
})
Vue.filter('dispDate', function (date, format) {
  var f = 'YYYY/MM/DD HH:mm'
  if (format) {
    f = format
  }
  return moment(date).format(f)
})

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
  rightSidebar(false) // ルーティングの際には一度必ず閉じる→書くページで必要に応じ復元する
  store.commit('CREAR_ALERTS') // Alerts初期化
  if (to.matched.some(record => record.meta.requiresAuth) && (!router.app.$store.state.token || router.app.$store.state.token === 'null')) {
    // this route requires auth, check if logged in
    // if not, redirect to login page.
    logger.debug(LABEL, 'Not authenticated')
    next({
      path: '/login',
      query: { redirect: to.fullPath }
    })
  } else {
    next()
  }
})
router.onReady(() => {
  store.commit('SET_PAGE_STATE', {loading: false}) // 画面を切り替えた場合、必ず読み込み中の表示をOFFにする
})

sync(store, router)

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
  fallbackLocale: 'en',
  messages: message
})

store.dispatch('setServerURI', serverURI)
.then(() => {
  return store.dispatch('loadUserInformation', {i18n})
}).then(() => {
  // Start out app!
  // eslint-disable-next-line no-new
  new Vue({
    el: '#root',
    router: router,
    store: store,
    render: h => h(AppView),
    i18n: i18n
  })
})
