import Promise from 'bluebird'
import api from '../../../api'
import logger from 'logger'

const LABEL = 'toggleDisplayComment.js'

export default (store, params) => {
  logger.debug(LABEL, 'toggleDisplayComment start')
  store.commit('pagestate/setPageState', {loading: true}, {root: true})
  store.commit('pagestate/clearAlerts', null, {root: true})
  return Promise.try(() => {
    return api.request('put', '/_api/articles/' + params.id + '/comments/' + params.comment.commentNo + '/collapse', params.comment)
  }).then(response => {
    logger.debug(LABEL, response.data)
  }).catch(error => {
    logger.error(LABEL, JSON.stringify(error))
    var msg = logger.buildResponseErrorMsg(error.response, {suffix: 'Please try again.'})
    store.commit('pagestate/addAlert', {
      type: 'warning',
      title: 'Error',
      content: msg
    }, {root: true})
    throw error
  }).finally(() => {
    store.commit('pagestate/setPageState', {loading: false}, {root: true})
  })
}
