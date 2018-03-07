import Promise from 'bluebird'
import api from '../../../api'
import logger from 'logger'

const LABEL = 'saveStocks.js'

export default (store, {id}) => {
  logger.trace(LABEL, 'saveStocks')
  return Promise.try(() => {
    let uri = '/_api/articles/' + id + '/stocks'
    return api.request('post', uri, store.state.stockSelect.items)
  }).then(() => {
    store.commit('pagestate/addAlert', {
      display: false,
      type: 'succcess',
      title: 'Well done!',
      content: 'You successfully save stocks.'
    }, {root: true})
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
