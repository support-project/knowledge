import Promise from 'bluebird'
import api from '../../api'
import logger from 'logger'

const LABEL = 'saveDraft.js'

export default (store) => {
  store.commit('SET_PAGE_STATE', {loading: true})
  store.commit('CREAR_ALERTS')
  return Promise.try(() => {
    const article = store.state.resources.article
    logger.info(LABEL, 'draft save articles to api. put data:\n' + JSON.stringify(article, null, '  '))
    return api.request('post', '/_api/drafts', article)
  }).then(response => {
    logger.debug(LABEL, JSON.stringify(response.data))
    store.commit('ADD_ALERT', {
      display: false,
      type: 'success',
      title: 'Well done!',
      content: 'You successfully save draft.'
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
