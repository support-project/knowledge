import Promise from 'bluebird'
import api from '../../../api'
import loadPaginationInformation from '../../../lib/utils/loadPaginationInformation'

import logger from 'logger'
const LABEL = 'getLikes.js'

export default (store) => {
  logger.debug(LABEL, 'showLikes')
  return Promise.try(() => {
    var uri = '/_api/articles/' + store.state.articleId + '/likes'
    if (store.state.pagination.offset) {
      uri += '?offset=' + store.state.pagination.offset
    }
    return api.request('get', uri)
  }).then(response => {
    store.state.items = response.data
    return loadPaginationInformation(response)
  }).then(pagination => {
    store.state.pagination = pagination
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
