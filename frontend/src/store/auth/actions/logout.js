import Promise from 'bluebird'
import api from '../../../api'
import logger from 'logger'

const LABEL = 'logout.js'

export default (store, params) => {
  logger.trace(LABEL, 'start logout: ' + JSON.stringify(params))
  store.commit('pagestate/setPageState', {loading: true}, {root: true})
  return Promise.try(() => {
    var token = store.getters['auth/getToken']
    return api.request('delete', '/_api/token/' + token, params)
    .then(response => {
      logger.debug(LABEL, JSON.stringify(response.data))
      return ''
    })
  }).catch(error => {
    logger.error(LABEL, error)
    return ''
  }).finally(() => {
    store.commit('pagestate/setPageState', {loading: false}, {root: true})
    store.commit('setToken', null)
    if (window.localStorage) {
      window.localStorage.setItem('token', null)
    }
  })
}
