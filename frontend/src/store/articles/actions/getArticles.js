import Promise from 'bluebird'
import logger from 'logger'
import api from '../../../api'
import setIcons from '../../../lib/utils/setIcons'
import loadPaginationInformation from '../../../lib/utils/loadPaginationInformation'

const LABEL = 'getArticles.js'

export default (store) => {
  store.commit('pagestate/setPageState', {loading: true}, {root: true}, {root: true})
  return Promise.try(() => {
    logger.debug(LABEL, 'get articles from api')
    var uri = '/_api/articles'
    if (store.state.pagination.offset) {
      uri += '?offset=' + store.state.pagination.offset
    }
    return api.request('get', uri, null)
  }).then(response => {
    logger.debug(LABEL, response.data)
    var articles = response.data
    articles.forEach(article => {
      setIcons(store, article)
    })
    store.commit('setArticles', articles)
    return loadPaginationInformation(response)
  }).then(pagination => {
    store.state.pagination = pagination
  }).catch(error => {
    logger.error(LABEL, error)
  }).finally(() => {
    store.commit('pagestate/setPageState', {loading: false}, {root: true}, {root: true})
  })
}
