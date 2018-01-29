import Promise from 'bluebird'
import api from '../../api'
import lang from 'lang'
import logger from 'logger'

const LABEL = 'saveArticle.js'

export default (store) => {
  store.commit('SET_PAGE_STATE', {loading: true})
  return Promise.try(() => {
    store.commit('CREAR_ALERTS')
    return lang.deepClone(store.state.article)
  }).then((article) => {
    article.type.items.forEach(element => {
      if (element.itemType === 11) {
        if (!lang.isString(element.itemValue)) {
          var vals = element.itemValue.join(',')
          element.itemValue = vals
        }
      }
    })
    if (article.knowledgeId) {
      logger.debug(LABEL, 'save articles to api. put data:\n' + JSON.stringify(article, null, '  '))
      return api.request('put', '/_api/articles/' + article.knowledgeId, article)
      .then(response => {
        logger.debug(LABEL, JSON.stringify(response.data))
        return article.knowledgeId
      })
    } else {
      logger.debug(LABEL, 'save articles to api. post data:\n' + JSON.stringify(article, null, '  '))
      return api.request('post', '/_api/articles', article)
      .then(response => {
        logger.debug(LABEL, JSON.stringify(response.data))
        store.state.article.knowledgeId = response.data.id
        return response.data.id
      })
    }
  }).catch(error => {
    logger.error(LABEL, JSON.stringify(error))
    var msg = logger.buildResponseErrorMsg(error.response, {suffix: 'Please try again.'})
    store.commit('ADD_ALERT', {
      type: 'warning',
      title: 'Error',
      content: msg
    })
    throw error
  }).finally(() => {
    store.commit('SET_PAGE_STATE', {loading: false})
  })
}
