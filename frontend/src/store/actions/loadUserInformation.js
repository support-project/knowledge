import Promise from 'bluebird'
import api from '../../api'
import logger from 'logger'

const LABEL = 'loadUserInformation.js'

var initUserInformation = (store) => {
  store.commit('SET_USER', {
    avatar: 'open.account/icon/',
    userName: 'anonymous'
  })
}

export default (store, params) => {
  logger.trace(LABEL, 'Load user information')
  if (!store.getters.IS_LOGINED) {
    initUserInformation(store)
    return
  }

  store.commit('SET_PAGE_STATE', {loading: true})
  return Promise.try(() => {
    return api.request('get', '/_api/me')
  }).then(response => {
    logger.info(LABEL, 'Get user information from api: ' + JSON.stringify(response.data))
    var user = response.data
    if (user && user.userId) {
      if (user.localeKey) {
        params.i18n.locale = user.localeKey
      }
      user.avatar = 'open.account/icon/' + user.userId // set my icon path
      store.commit('SET_USER', user)
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
    store.commit('SET_PAGE_STATE', {loading: false})
  })
}
