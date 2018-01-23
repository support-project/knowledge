import Promise from 'bluebird'
import api from '../../api'
import logger from 'logger'

const LABEL = 'loadUserInformation.js'

export default (store, params) => {
  logger.trace(LABEL, 'Load user information')
  store.commit('SET_PAGE_STATE', {loading: true})
  // params.i18n.locale = 'en'
  return Promise.try(() => {
    // Check local storage to handle refreshes
    if (window.localStorage) {
      // var localUserString = window.localStorage.getItem('user') || 'null'
      // var localUser = JSON.parse(localUserString)
      // logger.debug(LABEL, localUser)
      var token = window.localStorage.getItem('token')
      if (token) {
        return api.request('post', '/_api/token', {msg: token})
      }
    }
  }).then(response => {
    var token = response.data.token
    var user = response.data.user
    user.avatar = 'open.account/icon/' + user.userId
    if (token) {
      store.commit('SET_TOKEN', token)
      store.commit('SET_USER', user)
    } else {
      store.commit('SET_USER', {
        avatar: 'open.account/icon/',
        userName: 'anonymous'
      })
      store.commit('SET_TOKEN', '')
    }
    if (window.localStorage) {
      window.localStorage.setItem('token', token)
    }
  }).catch(error => {
    logger.warn(LABEL, JSON.stringify(error))
    store.commit('SET_USER', {
      avatar: 'open.account/icon/',
      userName: 'anonymous'
    })
    store.commit('SET_TOKEN', '')
  }).finally(() => {
    store.commit('SET_PAGE_STATE', {loading: false})
  })
}
