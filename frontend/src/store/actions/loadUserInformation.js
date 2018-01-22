import Promise from 'bluebird'
import api from '../../api'
import logger from 'logger'

const LABEL = 'loadUserInformation.js'

export default (state, params) => {
  logger.trace(LABEL, 'Load user information')
  state.commit('SET_PAGE_STATE', {loading: true})
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
      state.commit('SET_TOKEN', token)
      state.commit('SET_USER', user)
    } else {
      state.commit('SET_USER', {
        avatar: 'open.account/icon/',
        userName: 'anonymous'
      })
      state.commit('SET_TOKEN', '')
    }
    if (window.localStorage) {
      window.localStorage.setItem('token', token)
    }
  }).catch(error => {
    logger.warn(LABEL, JSON.stringify(error))
    state.commit('SET_USER', {
      avatar: 'open.account/icon/',
      userName: 'anonymous'
    })
    state.commit('SET_TOKEN', '')
  }).finally(() => {
    state.commit('SET_PAGE_STATE', {loading: false})
  })
}
