import Promise from 'bluebird'
import api from '../../api'
import logger from 'logger'

const LABEL = 'toggleDisplayComment.js'

export default (state, params) => {
  logger.debug(LABEL, 'toggleDisplayComment start')
  state.commit('SET_PAGE_STATE', {loading: true})
  state.commit('CREAR_ALERTS')
  return Promise.try(() => {
    return api.request('put', '/_api/articles/' + params.id + '/comments/' + params.comment.commentNo + '/collapse', params.comment)
  }).then(response => {
    logger.debug(LABEL, response.data)
    var drafts = response.data
    state.commit('SET_RESOURCES', {
      drafts: drafts
    })
  }).catch(error => {
    logger.error(LABEL, JSON.stringify(error))
    var msg = logger.buildResponseErrorMsg(error.response, {suffix: 'Please try again.'})
    state.commit('ADD_ALERT', {
      type: 'warning',
      title: 'Error',
      content: msg
    })
    throw error
  }).finally(() => {
    state.commit('SET_PAGE_STATE', {loading: false})
  })
}
