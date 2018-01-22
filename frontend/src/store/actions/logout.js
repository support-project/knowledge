import Promise from 'bluebird'
import api from '../../api'
import logger from 'logger'

const LABEL = 'logout.js'

export default (state, params) => {
  logger.trace(LABEL, 'start logout: ' + JSON.stringify(params))
  state.commit('SET_PAGE_STATE', {loading: true})
  return Promise.try(() => {
    var token = state.getters.GET_TOKEN
    return api.request('delete', '/_api/token/' + token, params)
    .then(response => {
      logger.debug(LABEL, JSON.stringify(response.data))
      return ''
    })
  }).catch(error => {
    logger.error(LABEL, error)
    return ''
  }).finally(() => {
    state.commit('SET_USER', {
      avatar: '/open.account/icon/',
      userName: 'anonymous'
    })
    state.commit('SET_TOKEN', null)
    if (window.localStorage) {
      // window.localStorage.setItem('user', null)
      window.localStorage.setItem('token', null)
    }
    state.commit('SET_PAGE_STATE', {loading: false})
  })
}
