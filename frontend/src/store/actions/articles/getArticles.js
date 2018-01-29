import Promise from 'bluebird'
import logger from 'logger'
import api from '../../../api'
import actionCommon from '../actionCommon'

const LABEL = 'getArticles.js'

export default (store) => {
  store.commit('SET_PAGE_STATE', {loading: true})
  return Promise.try(() => {
    logger.debug(LABEL, 'get articles from api')
    return api.request('get', '/_api/articles', null)
    .then(response => {
      logger.debug(LABEL, response.data)
      var articles = response.data
      articles.forEach(article => {
        actionCommon.setIcon(store, article)
      })
      store.commit('setArticles', articles)
    })
    .catch(error => {
      logger.error(LABEL, error)
    })
  }).finally(() => {
    store.commit('SET_PAGE_STATE', {loading: false})
  })
}
