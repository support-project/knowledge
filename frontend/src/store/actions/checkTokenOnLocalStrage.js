import Promise from 'bluebird'
import api from '../../api'
import logger from 'logger'

const LABEL = 'loadUserInformation.js'

export default (store) => {
  logger.trace(LABEL, 'check token saved local strage')
  if (!window.localStorage) {
    store.commit('SET_TOKEN', '')
    return false
  }
  var token = window.localStorage.getItem('token')
  if (!token) {
    store.commit('SET_TOKEN', '')
    return false
  }
  store.commit('SET_PAGE_STATE', {loading: true})
  return Promise.try(() => {
    return api.request('post', '/_api/token', {msg: token})
  }).then(response => {
    var token = response.data.token
    if (window.localStorage) {
      if (token) {
        window.localStorage.setItem('token', token)
      } else {
        window.localStorage.setItem('token', '')
      }
    }
    if (token) {
      store.commit('SET_TOKEN', token)
      return true
    } else {
      store.commit('SET_TOKEN', '')
      return false
    }
  }).catch(error => {
    logger.warn(LABEL, JSON.stringify(error))
    store.commit('SET_TOKEN', '')
    return false
  }).finally(() => {
    store.commit('SET_PAGE_STATE', {loading: false})
  })
}
