import Promise from 'bluebird'
import api from '../../api'
import logger from 'logger'

const LABEL = 'logout.js'

export default (context, params) => {
  logger.trace(LABEL, 'start logout: ' + JSON.stringify(params))
  context.commit('SET_PAGE_STATE', {loading: true})
  return Promise.try(() => {
    var token = context.getters.GET_TOKEN
    return api.request('delete', '/_api/token/' + token, params)
    .then(response => {
      logger.info(LABEL, JSON.stringify(response.data))
      return ''
    })
  }).catch(error => {
    logger.error(LABEL, error)
    return ''
  }).finally(() => {
    context.commit('SET_USER', {
      avatar: '/open.account/icon/',
      userName: 'anonymous'
    })
    context.commit('SET_TOKEN', null)
    if (window.localStorage) {
      window.localStorage.setItem('user', null)
      window.localStorage.setItem('token', null)
    }
    context.commit('SET_PAGE_STATE', {loading: false})
  })
}
