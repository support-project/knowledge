import Promise from 'bluebird'
import api from '../../../api'
import logger from 'logger'
import loadPaginationInformation from '../../../lib/utils/loadPaginationInformation'

const LABEL = 'getStocksForSelect.js'

export default (store, {id, offset}) => {
  logger.trace(LABEL, 'getStocksForSelect')
  if (offset === -1) {
    return
  }
  return Promise.try(() => {
    let uri = '/_api/articles/' + id + '/stocks'
    uri += '?limit=' + store.state.stockSelect.pagination.limit
    if (offset > 0) {
      uri += '&offset=' + offset
    }
    return api.request('get', uri, null)
  }).then(response => {
    store.state.stockSelect.items = response.data
    return loadPaginationInformation(response)
  }).then(pagination => {
    store.state.stockSelect.pagination = pagination
  }).catch(error => {
    logger.error(LABEL, JSON.stringify(error))
    var msg = logger.buildResponseErrorMsg(error.response, {suffix: 'Please try again.'})
    store.commit('pagestate/addAlert', {
      type: 'warning',
      title: 'Error',
      content: msg
    }, {root: true})
    throw error
  })
}
