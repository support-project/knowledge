import Promise from 'bluebird'
import api from '../../../api'
import logger from 'logger'

const LABEL = 'deleteDraft.js'

export default (store) => {
  if (!store.state.article.draftId) {
    return
  }
  const draftId = store.state.article.draftId
  store.commit('pagestate/setPageState', {loading: true}, {root: true})
  store.commit('pagestate/clearAlerts', null, {root: true})
  return Promise.try(() => {
    return api.request('delete', '/_api/drafts/' + draftId)
  }).then(response => {
    logger.debug(LABEL, JSON.stringify(response.data))
    store.commit('pagestate/addAlert', {
      display: false,
      type: 'success',
      title: 'Well done!',
      content: 'You successfully delete draft.'
    }, {root: true})
    return response.data.id
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
