import Promise from 'bluebird'
import jstz from 'jstz'
import api from '../../../api'

import logger from 'logger'
const LABEL = 'loadUserInformation.js'
var timezone = jstz.determine().name()

var initUserInformation = (store) => {
  store.commit('setUser', {
    avatar: 'open.account/icon/',
    userName: 'anonymous',
    localeKey: 'en',
    timezone: timezone,
    POINT: 0,
    configs: []
  })
}

export default (store, params) => {
  logger.trace(LABEL, 'Load user information')
  if (!store.rootGetters['auth/isLogined']) {
    initUserInformation(store)
    return
  }

  store.commit('pagestate/setPageState', {loading: true}, {root: true})
  return Promise.try(() => {
    return api.request('get', '/_api/me')
  }).then(response => {
    logger.debug(LABEL, 'Get user information from api: ' + JSON.stringify(response.data))
    var user = response.data
    if (user && user.userId) {
      if (user.localeKey) {
        if (params && params.i18n) {
          params.i18n.locale = user.localeKey
        }
      }
      user.avatar = 'open.account/icon/' + user.userId // set my icon path
      user.configs.forEach(element => {
        if (element.configName === 'POINT') {
          user[element.configName] = parseInt(element.configValue)
        } else {
          user[element.configName] = element.configValue
        }
      })
      if (!user.timezone) {
        user.timezone = timezone
      }

      store.commit('setUser', user)
      return true
    } else {
      initUserInformation(store)
      return false
    }
  }).catch(error => {
    logger.warn(LABEL, JSON.stringify(error))
    initUserInformation(store)
    return false
  }).finally(() => {
    store.commit('pagestate/setPageState', {loading: false}, {root: true})
  })
}
