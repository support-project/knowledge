import Promise from 'bluebird'
import api from '../../../api'
import loadPaginationInformation from '../../../lib/utils/loadPaginationInformation'

import logger from 'logger'
const LABEL = 'getArticles.js'

export default (store, id, offset) => {
  logger.debug(LABEL, 'getArticles')

  if (offset === -1) {
    return
  }
  store.state.articlePagination.offset = offset

  return Promise.try(() => {
    var uri = '/_api/stocks/' + id + '/articles'
    if (store.state.articlePagination.offset) {
      uri += '?offset=' + store.state.articlePagination.offset
    }
    return api.request('get', uri)
  }).then(response => {
    store.state.articles = response.data
    return loadPaginationInformation(response)
  }).then(pagination => {
    store.state.articlePagination = pagination
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
