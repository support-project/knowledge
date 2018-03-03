import Promise from 'bluebird'
import api from '../../../api'

import logger from 'logger'
const LABEL = 'getStock.js'

export default (store, id) => {
  logger.debug(LABEL, 'getStock')
  return Promise.try(() => {
    var uri = '/_api/stocks/' + id
    return api.request('get', uri, store.state.item)
  }).then(response => {
    store.state.item = response.data
    logger.info(LABEL, JSON.stringify(store.state.item))
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
