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
import VueLazyload from 'vue-lazyload'

import { sync } from 'vuex-router-sync'
import routes from './routes'
import store from './store'

import i18n from './lib/i18n'

// Import Helpers for filters
import { domain, count, prettyDate, pluralize } from './filters'

// Import Views - Top level
import AppView from './components/App.vue'

import rightSidebar from './lib/displayParts/rightSidebar'

import lang from 'lang'
import moment from 'moment-timezone'

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
  var f = 'YYYY/MM/DD HH:mm Z' // TODO datetime format
  if (format) {
    f = format
  }
  var timezone = store.state.user.user.timezone
  if (!timezone) {
    return moment(date).format(f)
  } else {
    return moment.utc(date).tz(timezone).format(f)
  }
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
  store.commit('pagestate/clearAlerts') // Alerts初期化
  if (to.matched.some(record => record.meta.requiresAuth) &&
    (!router.app.$store.getters['auth/getToken'] || router.app.$store.getters['auth/getToken'] === 'null')) {
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
  store.commit('pagestate/setPageState', {loading: false}) // 画面を切り替えた場合、必ず読み込み中の表示をOFFにする
})

sync(store, router)

Vue.use(VueLazyload, {
  preLoad: 1.3,
  error: './static/img/loader.gif',
  loading: './static/img/loader.gif',
  attempt: 1
})

store.dispatch('system/setServerURI', serverURI)
.then(() => {
  return store.dispatch('auth/checkTokenOnLocalStrage')
}).then(() => {
  return store.dispatch('user/loadUserInformation', {i18n})
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
