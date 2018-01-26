import Promise from 'bluebird'
import api from '../../api'
import logger from 'logger'

const LABEL = 'logout.js'

export default (store, params) => {
  logger.trace(LABEL, 'start logout: ' + JSON.stringify(params))
  store.commit('SET_PAGE_STATE', {loading: true})
  return Promise.try(() => {
    var token = store.getters.GET_TOKEN
    return api.request('delete', '/_api/token/' + token, params)
    .then(response => {
      logger.debug(LABEL, JSON.stringify(response.data))
      return ''
    })
  }).catch(error => {
    logger.error(LABEL, error)
    return ''
  }).finally(() => {
    store.commit('SET_PAGE_STATE', {loading: false})
    store.commit('SET_TOKEN', null)
    if (window.localStorage) {
      window.localStorage.setItem('token', null)
    }
  })
}
