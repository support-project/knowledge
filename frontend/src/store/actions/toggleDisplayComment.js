import Promise from 'bluebird'
import api from '../../api'
import logger from 'logger'

const LABEL = 'toggleDisplayComment.js'

export default (store, params) => {
  logger.debug(LABEL, 'toggleDisplayComment start')
  store.commit('SET_PAGE_STATE', {loading: true})
  store.commit('CREAR_ALERTS')
  return Promise.try(() => {
    return api.request('put', '/_api/articles/' + params.id + '/comments/' + params.comment.commentNo + '/collapse', params.comment)
  }).then(response => {
    logger.debug(LABEL, response.data)
    var drafts = response.data
    store.commit('SET_RESOURCES', {
      drafts: drafts
    })
  }).catch(error => {
    logger.error(LABEL, JSON.stringify(error))
    var msg = logger.buildResponseErrorMsg(error.response, {suffix: 'Please try again.'})
    store.commit('ADD_ALERT', {
      type: 'warning',
      title: 'Error',
      content: msg
    })
    throw error
  }).finally(() => {
    store.commit('SET_PAGE_STATE', {loading: false})
  })
}