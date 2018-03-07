import Promise from 'bluebird'
import api from '../../../api'

import logger from 'logger'
const LABEL = 'saveArticle.js'

export default (store, id) => {
  logger.debug(LABEL, 'saveArticle')
  return Promise.try(() => {
    var uri = '/_api/stocks/'
    if (id) {
      uri += id
      return api.request('put', uri, store.state.item)
    } else {
      return api.request('post', uri, store.state.item)
    }
  }).then(response => {
    store.state.item = response.data
    logger.trace(LABEL, JSON.stringify(store.state.item))
    return response.data
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
  })
}
