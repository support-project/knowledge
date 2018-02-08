import Promise from 'bluebird'
import logger from 'logger'
import api from '../../../api'
import setIcons from '../../../lib/utils/setIcons'

const LABEL = 'getArticles.js'

export default (store) => {
  store.commit('pagestate/setPageState', {loading: true}, {root: true}, {root: true})
  return Promise.try(() => {
    logger.debug(LABEL, 'get articles from api')
    return api.request('get', '/_api/articles', null)
    .then(response => {
      logger.debug(LABEL, response.data)
      var articles = response.data
      articles.forEach(article => {
        setIcons(store, article)
      })
      store.commit('setArticles', articles)
    })
    .catch(error => {
      logger.error(LABEL, error)
    })
  }).finally(() => {
    store.commit('pagestate/setPageState', {loading: false}, {root: true}, {root: true})
  })
}
