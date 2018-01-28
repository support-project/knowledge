import Promise from 'bluebird'
import api from '../../api'
import logger from 'logger'

const LABEL = 'deleteDraft.js'

export default (store) => {
  if (!store.state.resources.article.draftId) {
    return
  }
  const draftId = store.state.resources.article.draftId
  store.commit('SET_PAGE_STATE', {loading: true})
  store.commit('CREAR_ALERTS')
  return Promise.try(() => {
    return api.request('delete', '/_api/drafts/' + draftId)
  }).then(response => {
    logger.debug(LABEL, JSON.stringify(response.data))
    store.commit('ADD_ALERT', {
      display: false,
      type: 'success',
      title: 'Well done!',
      content: 'You successfully delete draft.'
    })
    return response.data.id
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
