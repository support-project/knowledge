import Promise from 'bluebird'
import api from '../../../api'
import logger from 'logger'

const LABEL = 'checkTokenOnLocalStrage.js'

export default (store) => {
  logger.trace(LABEL, 'check token saved local strage')
  if (!window.localStorage) {
    store.commit('setToken', '')
    return false
  }
  var token = window.localStorage.getItem('token')
  if (!token) {
    store.commit('setToken', '')
    return false
  }
  store.commit('pagestate/setPageState', {loading: true}, {root: true})
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
      store.commit('setToken', token)
      return true
    } else {
      store.commit('setToken', '')
      return false
    }
  }).catch(error => {
    logger.warn(LABEL, JSON.stringify(error))
    store.commit('setToken', '')
    return false
  }).finally(() => {
    store.commit('pagestate/setPageState', {loading: false}, {root: true})
  })
}
