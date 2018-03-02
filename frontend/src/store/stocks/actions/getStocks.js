import Promise from 'bluebird'
import api from '../../../api'
import loadPaginationInformation from '../../../lib/utils/loadPaginationInformation'

import logger from 'logger'
const LABEL = 'getStocks.js'

export default (store, offset) => {
  logger.debug(LABEL, 'getStocks')

  if (offset === -1) {
    return
  }
  store.state.pagination.offset = offset

  return Promise.try(() => {
    var uri = '/_api/stocks/'
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
